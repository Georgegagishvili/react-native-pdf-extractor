package com.pdfextractor

import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.module.annotations.ReactModule

import com.tom_roush.pdfbox.pdmodel.PDDocument;
import com.tom_roush.pdfbox.text.PDFTextStripper;
import com.tom_roush.pdfbox.android.PDFBoxResourceLoader;

import java.io.File
import java.io.FileInputStream

@ReactModule(name = PdfExtractorModule.NAME)
class PdfExtractorModule(reactContext: ReactApplicationContext) :
  NativePdfExtractorSpec(reactContext) {

  init {
      PDFBoxResourceLoader.init(reactContext)
  }

  override fun extractTextFromPdf(filePath: String, callback: Callback) {
    Thread {
        try {
            val file = File(filePath)
            val fileInputStream = FileInputStream(file)
            val document = PDDocument.load(fileInputStream)
            val stripper = PDFTextStripper()
            val text = stripper.getText(document)
            document.close()
            callback.invoke(null, text)
        } catch (e: Exception) {
            callback.invoke(e.message, null)
        }
    }.start()
  }

  companion object {
    const val NAME = "PdfExtractor"
  }
}
