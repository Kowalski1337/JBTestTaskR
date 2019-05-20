# JBTestTaskR
Test task for jetbrains internship. Project title: Code Insight For R Language

## Условие

Необходимо реализовать интерпретатор языка, описанного ниже. На стандартный вход подается программа на данном языке. Необходимо вывести значение последнего выражения в программе в десятичной системе счисления<br>
Используйте один терминал для всех имен переменных и имен типов.<br>

### Исходная грамматика
*`<character>`*  ::= "A" | "B" | "C" | "D" | "E" | "F" | "G" | "H" | "I" | "J" | "K" | "L" | "M" | "N" | "O" | "P" | "Q" | "R" | "S" | "T" | "U" | "V" | "W" | "X" | "Y" | "Z" | "a" | "b" | "c" | "d" | "e" | "f" | "g" | "h" | "i" | "j" | "k" | "l" | "m" | "n" | "o" | "p" | "q" | "r" | "s" | "t" | "u" | "v" | "w" | "x" | "y" | "z" | "_"<br>
*`<digit>`*   ::= "0" | "1" | "2" | "3" | "4" | "5" | "6" | "7" | "8" | "9"<br>
*`<number>`* ::= *`<digit> | <digit> <number>`*<br>
*`<identifier>`* ::= *`<character> | <identifier> <character>*`<br>
*`<operation>`* ::= "+" | "-" | "*" | "/" | "%" | ">" | "<" | "="<br>

*`<constant-expression>`* ::= *`"-" <number> | <number>`*<br>
*`<binary-expression>`* ::= *`"(" <expression> <operation> <expression>  ")"`*<br>
*`<argument-list>`* ::= *`<expression> | <expression> "," <argument-list>`*<br>
*`<call-expression>`* ::= *`<identifier> "(" <argument-list> ")"`*<br>
*`<if-expression>`* ::= *`"[" <expression> "]?(" <expression> "):("<expression>")"`*<br>


*`<expression>`* ::= *`<identifier> | <constant-expression> | <binary-expression> | <if-expression> | <call-expression>`*<br>

*`<parameter-list>`* ::= *`<identifier> | <identifier> "," <parameter-list>`*<br>

*`<function-definition>`* ::= *`<identifier>"(" <parameter_list> ")" "={" <expression> "}"`*<br>

*`<function-definition-list>`* : *`"" | <function-definition> <EOL> | <function-definition> <EOL> <function-definition-list>`*<br>

*`<program>`* ::= *`<function-definition-list> <expression>`*<br>
*`<EOL>`* - символ перевода строки --- *`\n`*, программа не содержит других пробельных символов(пробел, табуляция, и т.д.);

###Семантика
1. Все переменные имеют тип 32-битный Integer;<br>
2. Гаранитруется, что вычисления не приводят к переполнению;<br>
3. Все арифметические операции аналогичны соответствующим операциям для 32-битного int в языка Java;<br>
4. Операции сравнения возвращают 1 если сравнение истинно и 0 если ложно;<br>
5. <if-expression> исполняет второе выражение, если первое выражение не равно 0; иначе исполняет третье;<br>
6. <call-expression> вызывает функцию с соответствующим именем;<br>
7. Выражение вычисляются слева направо;<br>


###Оценка выполнения задания

1. Калькулятор: На вход подается корректная программа без <if-expression>, у которой <function-definition-list> пустой.<br>
Пример:
(2+2)<br>
Ответ:4<br>
Пример:(2+((3*4)/5))<br>
Ответ:4<br>
2. Поддержка <if-expression>: в программе присутствуют <if-expression><br>
Пример:[((10+20)>(20+10))]?{1}:{0}<br>
Ответ:0<br>
3. Поддержка функций: <function-definition-list> не пустой<br> 
Пример:<br>
g(x)={(f(x)+f((x/2)))}<br>
f(x)={[(x>1)]?{(f((x-1))+f((x-2)))}:{x}}<br>
g(10)<br>
Ответ:60<br>
4. Обработка ошибок:<br>
* Если программа не соответствует грамматике необходимо вывести:<br>
*`SYNTAX ERROR`*<br>

* Если в программе используется неопределенная переменная необходимо вывести:
*`PARAMETER NOT FOUND <name>:<line>`*<br>
здесь и далее *`<name>`* и *`<line>`* это ошибочное имя и номер строки на которой произошла ошибка

* Если программа вызывает функцию с неизвестным именем, то необходимо вывести:<br>
*`FUNCTION NOT FOUND <name>:<line>`*

* Если программа вызывает функцию с неверным числом аргументов, то необходимо вывести:<br>
*`ARGUMENT NUMBER MISMATCH <name>:<line>`*

* Если произошла ошибка выполнения необходимо, то вывести:<br>
*`RUNTIME ERROR <expression>:<line>`*<br>
*`<expression>`* --- выражение в котором произошла ошибка.<br>
Пример: 1 + 2 + 3 + 4 + 5<br>
Ответ: *`SYNTAX ERROR`*<br>
Пример:<br>
f(x)={y}<br>
f(10)<br>
Ответ:<br>
*`PARAMETER NOT FOUND y:1`*<br>
Пример:<br>
g(x)={f(x)}<br>
g(10)<br>
Ответ:<br>
*`FUNCTION NOT FOUND f:1`*<br>
Пример:<br>
g(x)={(x+1)}<br>
g(10,20)<br>
Ответ:<br>
*`ARGUMENT NUMBER MISMATCH g:2`*<br>
Пример:<br>
g(a,b)={(a/b)}<br>
g(10,0)<br>
Ответ:<br>
*`RUNTIME ERROR (a/b):1`*

###Сведение грамматики к LL(1)
Представленная в задании грамматика имеет одно лишние правило. Так же грамматика содержит правое ветвление а значит по 
следствию из теоремы о связи LL(1)-грамматики с множествами FIRST и FOLLOW она не является LL(1)-грамматикой. 
Чтоб избавиться от правого ветвления воспользуем алгоритмом леой факторизации. Я не буду пошагово описывать процесс 
сведения, а лишь укажу результат сведения. В процессе сведения возникла проблема, вызовы функций и дикларирование функций
могут иметь слишком большой префикс и если полностью сводить грамматику к LL(1), оставляя ее такой грамматика изменится 
до неузнаваемости и с ней с точки зрения логики будет тяжело работать, потому я принял решение запретить лексическому  
будущему лексическому анализатору принимать программы, где единственная строчка это вызов функции, это существенно 
упрощает процесс сведения грамматики к LL(1).


*`<character>`*  ::= "A" | "B" | "C" | "D" | "E" | "F" | "G" | "H" | "I" | "J" | "K" | "L" | "M" | "N" | "O" | "P" | "Q" | "R" | "S" | "T" | "U" | "V" | "W" | "X" | "Y" | "Z" | "a" | "b" | "c" | "d" | "e" | "f" | "g" | "h" | "i" | "j" | "k" | "l" | "m" | "n" | "o" | "p" | "q" | "r" | "s" | "t" | "u" | "v" | "w" | "x" | "y" | "z" | "_"<br>
*`<digit>`*   ::= "0" | "1" | "2" | "3" | "4" | "5" | "6" | "7" | "8" | "9"<br>
*`<extra-number>`* ::= *`"" | <digit> <extra-number>`*<br>
*`<number>`* ::= *`<digit> <extra-number>`*<br>
*`<extra-identifier>`* ::= *`"" | <character> <extra-identifier>`*<br>
*`<identifier>`* ::= *`<character> <extra-identifier>*`<br>
*`<operation>`* ::= "+" | "-" | "*" | "/" | "%" | ">" | "<" | "="<br>

*`<constant-expression>`* ::= *`"-" <number> | <number>`*<br>
*`<binary-expression>`* ::= *`"(" <expression> <operation> <expression>  ")"`*<br>
*`<extra-argument-list>`* ::= *`"" | "," <expression> <extra-argument-list> `*<br>
*`<argument-list>`* ::= *`<expression> <extra-argument-list>`*<br>
*`<call-expression>`* ::= *`"" | "(" <argument-list> ")"`*<br>
*`<if-expression>`* ::= *`"[" <expression> "]?(" <expression> "):("<expression>")"`*<br>

*`<expression'>`*::= *`<constant-expression> | <binary-expression> | <if-expression>`*<br>
*`<expression>`* ::= *`<identifier> <call-expression> | <expression'>`*<br>

*`<extra-parameter-list>`* ::= *`"" | "," <identifier> <extra-parameter-list>`*<br>

*`<parameter-list>`* ::= *`<identifier> <extra-parameter-list>`*<br>

*`<function-definition>`* ::= *`"(" <parameter_list> ")" "={" <expression> "}"`*<br>

*`<extra-function-definition-list>`* ::= *`"" | <identifier> <function-difinition-list>`* 

*`<function-definition-list>`* ::= *`<function-definition> <EOL> <extra-function-definition-list>`*<br>

*`<program>`* ::= *`<identifier>  <function-definition-list> <expression> | <expression'>`*<br>
*`<EOL>`* - символ перевода строки --- *`\n`*, программа не содержит других пробельных символов(пробел, табуляция, и т.д.);

###Построение множеств FIRST и FOLLOW
*`<identifier>`* и *`<number>`* будут считаться терминалами, с соответствующими регулярными выражениями<br>
*`<identifier>`* ::= `[a-zA-Z_]+`<br>
*`<number>`* ::= `[0-9]+`<br>

Так же введем еще один терминал `OP`, которому будет соответсвовать все возможные операции

Нетерминал | Описание | FIRST | FOLLOW
---|---|---|---
*`<program>`* | Код программы | *`<identifier>`*,*`<number>`*, `-`, `[`, `(` | `$`
*`<function-definition-list>`* | Список функций | `(` | *`<identifier>`*,*`<number>`*, `-`, `[`, `(`
*`<extra-function-definition-list>`* | Продолжение списка функций | *`<identifier>`*, `eps` | *`<identifier>`*, *`<number>`*, `-`, `[`, `(` 
*`<function-definition>`* | Функция | `(` | `EOL`
*`<parameter-list>`* | Список параметров функции | *`<identifier>`* | `)`
*`<extra-parameter-list>`* | Продолжние списка параметров функции | `eps`, `,` | `)`
*`<expression>`* | Выражение | *`<identifier>`*,*`<number>`*, `-`, `[`, `(` | `$`, `}`, `]`, `,`, `OP`
*`<expression'>`* | Выражение | *`<number>`*, `-`, `[`, `(` | `$`, `}`, `]`, `,`, `OP`
*`<if-expression>`* | Условный оператор | `[` | `$`, `}`, `]`, `,`, `OP`
*`<call-expression>`* | Вызов функции | `&`, `eps` | `$`, `}`, `]`, `,`, `OP`
*`<argument-list>`* | Список аргументов передаваемых в функцию | *`<identifier>`*,*`<number>`*, `-`, `[`, `(` | `)`
*`<extra-argument-list>`* | Продолжение списка аргументов передаваемых в функцию | `,`, `eps` | `)`
*`<binary-expression>`* | Бинарная операция над двумя выражениями | `(` | `$`, `}`, `]`, `,`, `OP`
*`<constant-expression>`* | Число | *`<number>`*, `-` | `$`, `}`, `]`, `,`, `OP`
*`<operation>`* | Бинарная операция | `OP` | *`<identifier>`*,*`<number>`*, `-`, `[`, `(`
