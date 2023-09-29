import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.FileReader
import java.io.FileWriter

class MarkdownParser : CodeParser{
    override fun parseCode(filename: String){
        //****************************************************************
        // our reader and writer
        val lines = mutableListOf<String>()
        val reader = BufferedReader(FileReader(filename))
        val writer = BufferedWriter(FileWriter("ParsedHTML.html"))

        //****************************************************************
        // Our Regex patterns
        val heading1 = Regex("\\s?+#(.+)")
        val heading2 = Regex("\\s?+##(.+)")
        val heading3 = Regex("\\s?+###(.+)")
        val italics = Regex("\\s?+\\*(.*)\\*")
        val boldtext = Regex("\\s?+\\*\\*(.*)\\*\\*")
        val blockquote = Regex("\\s?+>(.*)")
        val horizonalLine = Regex(".*?(---).*?")

        //****************************************************************
        // reads our markdown file in line by line and places it into lines
        var line:String?

        while(reader.readLine().also{line = it} != null){
            line?.let{
                lines.add(it)
            }
        }

        //****************************************************************
        // Converts the line and writes it to the new file

        val md = MarkdownNode()

        // get a line
        for(line in lines) {
            // check if the line matches one of our html formats

            //###
            if (heading3.matches(line)) {
                val matched = heading3.matchEntire(line)
                matched?.let {
                    writer.write(md.toHTML(matched.groupValues[1], 1))
                    writer.newLine()
                }
            }
            //##
            else if (heading2.matches(line)) {
                val matched = heading2.matchEntire(line)
                matched?.let {
                    writer.write(md.toHTML(matched.groupValues[1], 2))
                    writer.newLine()
                }
            }
            //#
            else if (heading1.matches(line)) {
                val matched = heading1.matchEntire(line)
                matched?.let {
                    writer.write(md.toHTML(matched.groupValues[1], 3))
                    writer.newLine()
                }
            }
            //** **
            else if (boldtext.matches(line)) {
                val matched = boldtext.matchEntire(line)
                matched?.let {
                    writer.write(md.toHTML(matched.groupValues[1], 4))
                    writer.newLine()
                }
            }
            //* *
            else if (italics.matches(line)) {
                val matched = italics.matchEntire(line)
                matched?.let {
                    writer.write(md.toHTML(matched.groupValues[1], 5))
                    writer.newLine()
                }
            }
            //>
            else if (blockquote.matches(line)) {
                val matched = blockquote.matchEntire(line)
                matched?.let {
                    writer.write(md.toHTML(matched.groupValues[1], 6))
                    writer.newLine()
                }
            }
            //---
            else if (horizonalLine.matches(line)) {
                val matched = horizonalLine.matchEntire(line)
                matched?.let {
                    writer.write(md.toHTML(matched.groupValues[1], 7))
                    writer.newLine()
                }
            }
            // blank line or text
            else {
                    writer.write(md.toHTML(line, 8))
                    writer.newLine()
            }
        }
        // close the writer
        writer.close()
    }
}