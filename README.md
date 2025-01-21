# DatabaseProject
This project about usage Data Base in web-application in java. Manage your restaurant's data easily with this web app.

# Як запустити проект

1. Переконайтеся, що у вас встановлений [Docker](https://www.docker.com/).
2. Клонуйте репозиторій:
   bash
   git clone https://github.com/your-username/your-repo.git
   cd your-repo
3. Впевніться що змінили параметри в середовищі, або в файлах application.properties, copmpose.yaml, а саме ${LOGIN}, та ${PASSWORD}, на необхідні.
4. Запустіть проект за допомогою Docker(з кореневої папки проєкту):
docker-compose up
5. Відкрийте браузер і перейдіть за адресою: http://localhost:8080
6. Щоб зупинити контейнер:
docker-compose down

# Або

1. Завантажте PostgreSQL: [PostgreSQL](https://www.postgresql.org/) 
Maven: [Maven](https://maven.apache.org/download.cgi)
JDK 21: [JDK](https://www.oracle.com/cis/java/technologies/downloads/)
2. Впевніться що змінили параметри в середовищі, або в файлах application.properties, copmpose.yaml, а саме ${LOGIN}, та ${PASSWORD}, на необхідні.
3. Створіть базу даних та змініть посилання до неї в файлах application.properties, copmpose.yaml
4. Запакуйте проект завдяки Maven:
maven package
5. Запустіть проект за допомогою вашої IDE, або напряму з папки target
6. Відкрийте браузер і перейдіть за адресою: http://localhost:8080
