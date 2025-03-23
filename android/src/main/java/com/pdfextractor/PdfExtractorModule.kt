package com.pdfextractor

import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReactMethod
import com.facebook.react.bridge.Promise
import com.tom_roush.pdfbox.pdmodel.PDDocument
import com.tom_roush.pdfbox.text.PDFTextStripper
import com.tom_roush.pdfbox.android.PDFBoxResourceLoader
import java.io.File
import java.io.FileInputStream

@ReactModule(name = PdfExtractorModule.NAME)
class PdfExtractorModule(reactContext: ReactApplicationContext) :
  NativePdfExtractorSpec(reactContext) {

  init {
      PDFBoxResourceLoader.init(reactContext)
  }

  @ReactMethod
  override fun extractTextFromPdf(filePath: String, promise: Promise) {
    Thread {
        try {
            val file = File(filePath)
            val fileInputStream = FileInputStream(file)
            val document = PDDocument.load(fileInputStream)
            val stripper = PDFTextStripper()
            val text = stripper.getText(document)
            document.close()
            promise.resolve(text)
        } catch (e: Exception) {
            promise.reject("PdfExtractorError", e)
        }
    }.start()
  }

  override fun getName(): String {
      return NAME
  }

  companion object {
    const val NAME = "PdfExtractor"
  }
}
