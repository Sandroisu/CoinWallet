package ru.slatinin.nytnews.data

import android.util.Log
import org.xml.sax.Attributes
import org.xml.sax.InputSource
import org.xml.sax.SAXException
import org.xml.sax.XMLReader
import org.xml.sax.helpers.DefaultHandler
import java.io.IOException
import java.net.URL
import javax.xml.parsers.ParserConfigurationException
import javax.xml.parsers.SAXParser
import javax.xml.parsers.SAXParserFactory


class RssReader : DefaultHandler() {
    private var content: StringBuilder? = null
    private var inChannel = false
    private var inImage = false
    private var inItem = false

    private val items = ArrayList<Item>()
    private val channel = Channel()

    private var lastItem: Item? = null


    fun RssParser(url: String?) {
        try {
            val spf: SAXParserFactory = SAXParserFactory.newInstance()
            val sp: SAXParser = spf.newSAXParser()
            val xr: XMLReader = sp.getXMLReader()
            val sourceUrl = URL(url)
            xr.setContentHandler(this)
            xr.parse(InputSource(sourceUrl.openStream()))
        } catch (e: ParserConfigurationException) {
            e.printStackTrace()
        } catch (e: SAXException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }


    class Item {
        var title: String? = null
        var description: String? = null
        var link: String? = null
        var category: String? = null
        var pubDate: String? = null
        var guid: String? = null
        var imageUrl: String? = null
    }


    class Channel {
        var title: String? = null
        var description: String? = null
        var link: String? = null
        var lastBuildDate: String? = null
        var generator: String? = null
        var imageUrl: String? = null
        var imageTitle: String? = null
        var imageLink: String? = null
        var imageWidth: String? = null
        var imageHeight: String? = null
        var imageDescription: String? = null
        var language: String? = null
        var copyright: String? = null
        var pubDate: String? = null
        var category: String? = null
        var ttl: String? = null
    }


    @Throws(SAXException::class)
    override fun startDocument() {
        Log.i("LOG", "StartDocument")
    }


    @Throws(SAXException::class)
    override fun endDocument() {
        Log.i("LOG", "EndDocument")
    }

    @Throws(SAXException::class)
    override fun startElement(uri: String?, localName: String, qName: String?, atts: Attributes?) {
        if (localName.equals("image", ignoreCase = true)) {
            inImage = true
        }
        if (localName.equals("channel", ignoreCase = true)) {
            inChannel = true
        }
        if (localName.equals("item", ignoreCase = true)) {
            lastItem = Item()
            items.add(lastItem!!)
            inItem = true
        }
        if (localName.equals("enclosure", ignoreCase = true)) {
            val imageUrl = atts?.getValue("url")
            imageUrl?.let {
                lastItem?.imageUrl = it
            }
        }
        if (localName.equals("content", ignoreCase = true)) {
            val imageUrl = atts?.getValue("url")
            imageUrl?.let {
                lastItem?.imageUrl = it
            }
        }
        content = StringBuilder()
    }


    @Throws(SAXException::class)
    override fun endElement(uri: String?, localName: String, qName: String?) {
        if (localName.equals("image", ignoreCase = true)) {
            inImage = false
        }
        if (localName.equals("channel", ignoreCase = true)) {
            inChannel = false
        }
        if (localName.equals("item", ignoreCase = true)) {
            inItem = false
        }
        if (localName.equals("title", ignoreCase = true)) {
            if (content == null) {
                return
            }
            if (inItem) {
                lastItem!!.title = content.toString()
            } else if (inImage) {
                channel.imageTitle = content.toString()
            } else if (inChannel) {
                channel.title = content.toString()
            }
            content = null
        }
        if (localName.equals("description", ignoreCase = true)) {
            if (content == null) {
                return
            }
            if (inItem) {
                lastItem!!.description = content.toString()
            } else if (inImage) {
                channel.imageDescription = content.toString()
            } else if (inChannel) {
                channel.description = content.toString()
            }
            content = null
        }

        if (localName.equals("link", ignoreCase = true)) {
            if (content == null) {
                return
            }
            if (inItem) {
                lastItem!!.link = content.toString()
            } else if (inImage) {
                channel.imageLink = content.toString()
            } else if (inChannel) {
                channel.link = content.toString()
            }
            content = null
        }
        if (localName.equals("category", ignoreCase = true)) {
            if (content == null) {
                return
            }
            if (inItem) {
                lastItem!!.category = content.toString()
            } else if (inChannel) {
                channel.category = content.toString()
            }
            content = null
        }
        if (localName.equals("pubDate", ignoreCase = true)) {
            if (content == null) {
                return
            }
            if (inItem) {
                lastItem!!.pubDate = content.toString()
            } else if (inChannel) {
                channel.pubDate = content.toString()
            }
            content = null
        }
        if (localName.equals("guid", ignoreCase = true)) {
            if (content == null) {
                return
            }
            lastItem!!.guid = content.toString()
            content = null
        }
        if (localName.equals("url", ignoreCase = true)) {
            if (content == null) {
                return
            }
            channel.imageUrl = content.toString()
            content = null
        }
        if (localName.equals("width", ignoreCase = true)) {
            if (content == null) {
                return
            }
            channel.imageWidth = content.toString()
            content = null
        }
        if (localName.equals("height", ignoreCase = true)) {
            if (content == null) {
                return
            }
            channel.imageHeight = content.toString()
            content = null
        }
        if (localName.equals("language", ignoreCase = true)) {
            if (content == null) {
                return
            }
            channel.language = content.toString()
            content = null
        }
        if (localName.equals("copyright", ignoreCase = true)) {
            if (content == null) {
                return
            }
            channel.copyright = content.toString()
            content = null
        }
        if (localName.equals("ttl", ignoreCase = true)) {
            if (content == null) {
                return
            }
            channel.ttl = content.toString()
            content = null
        }
    }


    @Throws(SAXException::class)
    override fun characters(ch: CharArray?, start: Int, length: Int) {
        if (content == null) {
            return
        }
        content!!.append(ch, start, length)
    }


    fun getItem(index: Int): Item {
        return items[index]
    }

}