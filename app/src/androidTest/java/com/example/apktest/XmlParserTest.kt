package com.example.apktest

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.apktest.xml.XmlParser
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class XmlParserTest : XmlParser {
    @Test
    fun useAppContext() {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        val rawXmlResource = appContext.resources.openRawResource(R.raw.entries)
        val entries = parse(rawXmlResource)
        assertEquals(20284, entries.size)
    }
}
