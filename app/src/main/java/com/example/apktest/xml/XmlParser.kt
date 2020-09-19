package com.example.apktest.xml

import com.example.apktest.db.Product
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserException
import org.xmlpull.v1.XmlPullParserFactory
import java.io.IOException
import java.io.InputStream

private val ns: String? = null

interface XmlParser {

    @Throws(XmlPullParserException::class, IOException::class)
    fun parse(inputStream: InputStream): List<Product> {
        inputStream.use { stream ->
            val parser: XmlPullParser = XmlPullParserFactory.newInstance().newPullParser()
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false)
            parser.setInput(stream, null)
            parser.nextTag()
            return readFeed(parser)
        }
    }

    @Throws(XmlPullParserException::class, IOException::class)
    private fun readFeed(parser: XmlPullParser): List<Product> {
        val entries = mutableListOf<Product>()

        parser.require(XmlPullParser.START_TAG, ns, "artiklar")
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.eventType != XmlPullParser.START_TAG) {
                continue
            }
            // Starts by looking for the entry tag
            if (parser.name == "artikel") {
                val element = readEntry(parser)
                if (element.percent != "0.00%") {
                    entries.add(element)
                }
            } else {
                skip(parser)
            }
        }
        return entries
    }

    // Parses the contents of an entry. If it encounters a title, summary, or link tag, hands them off
    // to their respective "read" methods for processing. Otherwise, skips the tag.
    @Throws(XmlPullParserException::class, IOException::class)
    private fun readEntry(parser: XmlPullParser): Product {
        parser.require(XmlPullParser.START_TAG, ns, "artikel")
        val entry = Product("-1").apply {
            while (parser.next() != XmlPullParser.END_TAG) {
                if (parser.eventType != XmlPullParser.START_TAG) {
                    continue
                }
                parseIfTagIsValid(parser)
            }
            this.apk = calculateApk()
        }
        return entry
    }

    private fun Product.parseIfTagIsValid(parser: XmlPullParser) {
        when (parser.name) {
            "nr" -> this.number = read(parser, parser.name)
            "Namn" -> this.name = read(parser, parser.name)
            "Namn2" -> this.name2 = read(parser, parser.name)
            "Prisinklmoms" -> this.price = read(parser, parser.name)
            "Volymiml" -> this.volume = read(parser, parser.name)
            "PrisPerLiter" -> this.litrePrice = read(parser, parser.name)
            "Alkoholhalt" -> this.percent = read(parser, parser.name)
            "Varugrupp" -> this.productGroup = read(parser, parser.name)
            "Typ" -> this.type = read(parser, parser.name)
            else -> skip(parser)
        }
    }

    @Throws(IOException::class, XmlPullParserException::class)
    private fun read(parser: XmlPullParser, tag: String): String {
        parser.require(XmlPullParser.START_TAG, ns, tag)
        val title = readText(parser)
        parser.require(XmlPullParser.END_TAG, ns, tag)
        return title
    }

    @Throws(IOException::class, XmlPullParserException::class)
    private fun readText(parser: XmlPullParser): String {
        var result = ""
        if (parser.next() == XmlPullParser.TEXT) {
            result = parser.text
            parser.nextTag()
        }
        return result
    }

    @Throws(XmlPullParserException::class, IOException::class)
    private fun skip(parser: XmlPullParser) {
        if (parser.eventType != XmlPullParser.START_TAG) {
            throw IllegalStateException()
        }
        var depth = 1
        while (depth != 0) {
            when (parser.next()) {
                XmlPullParser.END_TAG -> depth--
                XmlPullParser.START_TAG -> depth++
            }
        }
    }
}
