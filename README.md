# archiver

Сделать clone этого репозитория

Из коммандной строки в папке с pom.xml выполнить команду: maven clean install.(Или выполнить ее средствами intelliJ Idea)

Перейти в папку target и использовать jar файл для тестирования приложения

Архивация

java -jar <приложение> <файлы для архивации через пробел>  > <файл для записи инфо>

деархивация

cat <файл для записи инфо> | java -jar <приложение>

Тесты над архиватором, сегодня после работы


![alt text](https://github.com/Alexey111231/archiver/blob/master/Archiver%20architectur.png)
