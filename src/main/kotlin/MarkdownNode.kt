open class MarkdownNode : Node() {

    fun toHTML(text: String, type: Int): String {

        // Returns the right format based on the type number
        return when(type){
            1 -> "<h3>$text</h3>"
            2 -> "<h2>$text</h2>"
            3 -> "<h1>$text</h1>"
            4 -> "<b>$text</b>"
            5 -> "<i>$text</i>"
            6 -> "<blockquote>$text</blockquote>"
            else -> text
        }
    }
}