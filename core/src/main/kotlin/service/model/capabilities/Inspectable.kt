package service.model.capabilities

interface Inspectable {
    val description: String
    val matchers: List<String>
}