import PdfExtractor from './NativePdfExtractor';

export function extractTextFromPdf(filePath: string): Promise<string> {
  return PdfExtractor.extractTextFromPdf(filePath);
}
