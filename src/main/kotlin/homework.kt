import java.io.File

fun main() {

    var path: String = "C:\\Users\\legion\\Downloads\\KotlinHomework004\\src\\main\\kotlin\\textfile.json"

    var nameAndPhone = mutableMapOf("" to "")
    var nameAndEmail = mutableMapOf("" to "")

    var person = Person(nameAndPhone, nameAndEmail, path)
    readCommand(person, nameAndPhone, nameAndEmail, path)

}

data class Person(var nameAndPhone: Map<String, String>, var nameAndEmail: Map<String, String>, var path: String)

class export(var person: Person, var nameAndPhone: MutableMap<String, String>, var nameAndEmail: MutableMap<String, String>, var path: String) : Command{

    override fun doing(){

        if(nameAndPhone.toString() != "{=}"){
            File(path).writeText(nameAndPhone.toString())
        }
        if(nameAndEmail.toString() != "{=}"){
            File(path).appendText(nameAndEmail.toString())
        }

        readCommand(person, nameAndPhone, nameAndEmail, path)
    }
}













sealed interface Command{
    fun doing()
    fun isValid(value1: Regex, value2: String): Boolean{
        if(value1.containsMatchIn(value2)){
            return true
        }
        return false
    }
}

fun readCommand(person: Person, nameAndPhone: MutableMap<String, String>, nameAndEmail: MutableMap<String, String>, path: String): Command{
    println("""
                Выберите действие:
                1 - exit
                2 - help
                3 - add new abonent and phone
                4 - add new abonent and email
                5 - show
                6 - showAll
                7 - find
                8 - export
            """)
    var choise: String? = readlnOrNull()
    if(choise.equals("1")){
        EndClass().doing()
        return EndClass()
    }else if(choise.equals("2")){
        Information(person, nameAndPhone, nameAndEmail, path).doing()
        return Information(person, nameAndPhone, nameAndEmail, path)
    }else if(choise.equals("3")){
        AddPhone(person, nameAndPhone, nameAndEmail, path).doing()
        return AddPhone(person, nameAndPhone, nameAndEmail, path)
    }else if(choise.equals("4")){
        AddEmail(person, nameAndPhone, nameAndEmail, path).doing()
        return AddEmail(person, nameAndPhone, nameAndEmail, path)
    }else if(choise.equals("5")){
        Show(person, nameAndPhone, nameAndEmail, path).doing()
        return Show(person, nameAndPhone, nameAndEmail, path)
    }else if(choise.equals("6")){
        ShowAll(person, nameAndPhone, nameAndEmail, path).doing()
        return ShowAll(person, nameAndPhone, nameAndEmail, path)
    }else if(choise.equals("7")){
        findAbonent(person, nameAndPhone, nameAndEmail, path).doing()
        return findAbonent(person, nameAndPhone, nameAndEmail, path)
    }else if(choise.equals("8")){
        export(person, nameAndPhone, nameAndEmail, path).doing()
        return findAbonent(person, nameAndPhone, nameAndEmail, path)
    }else{
        readCommand(person, nameAndPhone, nameAndEmail, path)
        return readCommand(person, nameAndPhone, nameAndEmail, path)
    }
}













class Show(var person: Person, var nameAndPhone: MutableMap<String, String>, var nameAndEmail: MutableMap<String, String>, var path: String) : Command{

    override fun doing(){
        var count: Int = 0
        println("Введите имя искомого абонента: ")
        var findName = readLine()
        for(abonentPhone in nameAndPhone){
            if(abonentPhone.key == ""){
                println("Список абонентов с номером телефона пуст")
                count = 1
            }else{
                if(findName == abonentPhone.key){
                    println("-${abonentPhone.key}: ${abonentPhone.value}")
                    count++
                }
            }
        }
        if(count == 0){
            println("У абонента ${findName} нет номера телефона")
        }
        count = 0
        for(abonentEmail in nameAndEmail){
            if(abonentEmail.key == ""){
                println("Список абонентов с адресом электронной почты пуст")
                count = 1
            }else{
                if(findName == abonentEmail.key){
                    println("-${abonentEmail.key}: ${abonentEmail.value}")
                    count++
                }
            }
        }
        if(count == 0){
            println("У абонента ${findName} нет адреса электронной почты")
        }
        readCommand(person, nameAndPhone, nameAndEmail, path)
    }
}

class ShowAll(var person: Person, var nameAndPhone: MutableMap<String, String>, var nameAndEmail: MutableMap<String, String>, var path: String) : Command{

    override fun doing(){
        for(abonentPhone in nameAndPhone){
            if(abonentPhone.key == ""){
                println("Список абонентов с номером телефона пуст")
            }else{
                println("-${abonentPhone.key}: ${abonentPhone.value}")
            }
        }
        for(abonentEmail in nameAndEmail){
            if(abonentEmail.key == ""){
                println("Список абонентов с адресом электронной почты пуст")
            }else{
                println("-${abonentEmail.key}: ${abonentEmail.value}")
            }
        }
        readCommand(person, nameAndPhone, nameAndEmail, path)
    }
}


class findAbonent(var person: Person, var nameAndPhone: MutableMap<String, String>, var nameAndEmail: MutableMap<String, String>, var path: String) : Command{

    override fun doing(){
        println("Введите номер телефона или адрес электронной почты для поиска абонента:")
        var count: Int = 0
        var findElement = readLine()
        for(abonentPhone in nameAndPhone){
            if(abonentPhone.key == ""){
                println("Список абонентов с номером телефона пуст")
            }else{
                if(findElement == abonentPhone.value){
                    println("-${findElement}: ${abonentPhone.key}")
                    count++
                }
            }
        }
        for(abonentEmail in nameAndEmail){
            if(abonentEmail.key == ""){
                println("Список абонентов с адресом электронной почты пуст")
            }else{
                if(findElement == abonentEmail.value){
                    println("-${findElement}: ${abonentEmail.key}")
                    count++
                }
            }
        }
        if(count == 0){
            println("Абонент не найден!")
        }
        readCommand(person, nameAndPhone, nameAndEmail, path)
    }
}


class EndClass : Command{
    override fun doing(){
        println("До свидания!")
        return
    }
}


class Information(var person: Person, var nameAndPhone: MutableMap<String, String>, var nameAndEmail: MutableMap<String, String>, var path: String) : Command{
    override fun doing() {
        println("""
                Здравствуйте! Вы находитесь в телефонной книге.
                Тут вы можете добавить имя и номер телефона нового 
                адресата или имя и адрес электронной почты нового
                адресата
                -При добавлении имени абонента используйте только 
                буквы!
                -При добавлении номера телефона используйте только
                цифры от 0 до 9 или символ + ("плюс") перед номером
                телефона
                -При добавлении адреса электронной почты не забудьте
                использовать символ @ ("собака") и символ . ("точка")
            """)
        readCommand(person, nameAndPhone, nameAndEmail, path)
    }
}

class AddPhone (var person: Person, var nameAndPhone: MutableMap<String, String>, var nameAndEmail: MutableMap<String, String>, var path: String) : Command{

    override fun doing() {
        println("""
                Введите данные нового абонента в соответствии с шаблоном
                add <Имя> phone <Номер телефона> 
            """
        )
        var abonent: String? = readlnOrNull()
        if (abonent != null) {
            var arrayAbonent = abonent.split(" ")
            if (arrayAbonent.size != 4) {
                println(
                    """
                    ПРИ ДОБАВЛЕНИИ НОВОГО АБОНЕНТА ВОЗНИКЛА ОШИБКА!!!
                    ПОПРОБУЙТЕ СНОВА
                """
                )
                readCommand(person, nameAndPhone, nameAndEmail, path)
            }

            var name: String = arrayAbonent[1]
            var secondAbonentParametr: String = arrayAbonent[3]


            var regexName = "^[a-z]+$|^[а-я]+$".toRegex(RegexOption.IGNORE_CASE)
            var regexPhone = "^[+][0-9]+$|^[0-9]+$".toRegex()


            when{
                isValid(regexName,name) && isValid(regexPhone,secondAbonentParametr) -> {
                    println("Абонент $name с номером телефона $secondAbonentParametr добавлен")
                    nameAndPhone.remove("")
                    nameAndPhone.put(name, secondAbonentParametr)
                    readCommand(person, nameAndPhone, nameAndEmail, path)
                }
                else -> {
                    Information(person, nameAndPhone, nameAndEmail, path).doing()
                    readCommand(person, nameAndPhone, nameAndEmail, path)
                }

            }

        } else {
            Information(person, nameAndPhone, nameAndEmail, path).doing()
            readCommand(person, nameAndPhone, nameAndEmail, path)
        }
    }
}


class AddEmail (var person: Person, var nameAndPhone: MutableMap<String, String>, var nameAndEmail: MutableMap<String, String>, var path: String) : Command{

    override fun doing() {
        println("""
                Введите данные нового абонента в соответствии с шаблоном
                add <Имя> email <Адрес электронной почты>:
            """
        )
        var abonent: String? = readlnOrNull()
        if (abonent != null) {
            var arrayAbonent = abonent.split(" ")
            if (arrayAbonent.size != 4) {
                println(
                    """
                    ПРИ ДОБАВЛЕНИИ НОВОГО АБОНЕНТА ВОЗНИКЛА ОШИБКА!!!
                    ПОПРОБУЙТЕ СНОВА
                """
                )
                readCommand(person, nameAndPhone, nameAndEmail, path)
            }

            var name: String = arrayAbonent[1]
            var secondAbonentParametr: String = arrayAbonent[3]


            var regexName = "^[a-z]+$|^[а-я]+$".toRegex(RegexOption.IGNORE_CASE)
            var regexemail = "\\b[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,4}\\b".toRegex(RegexOption.IGNORE_CASE)



            when{
                isValid(regexName,name) && isValid(regexemail,secondAbonentParametr) -> {
                    println("Абонент $name с адресом электронной почты $secondAbonentParametr добавлен")
                    nameAndEmail.remove(nameAndEmail.remove(""))
                    nameAndEmail.put(name, secondAbonentParametr)
                    readCommand(person, nameAndPhone, nameAndEmail, path)
                }
                else -> {
                    Information(person, nameAndPhone, nameAndEmail, path).doing()
                    readCommand(person, nameAndPhone, nameAndEmail, path)
                }

            }

        } else {
            Information(person, nameAndPhone, nameAndEmail, path).doing()
            readCommand(person, nameAndPhone, nameAndEmail, path)
        }
    }
}








