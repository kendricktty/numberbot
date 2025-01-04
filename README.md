# Numberbot
#### A Spring Boot-based Telegram bot that generates random numbers or powers of 2

Done by:
* Kendrick Teo

## About This Project

Bots on Telegram, the messaging app, are typically created with Python, but other languages, such as Java, may also be used to achieve the same objective. This project is my first foray into Telegram bots, concurrently serving as an exercise on object-oriented programming with **Java** and application development with **Spring Boot**. It generates random numbers and powers of 2 from 32 (2^5) and beyond, and is an evolution from earlier iterations that used a command line interface.

## Usage

### System requirements

To sample this project, you will need the following:

#### Required:

- A Java Development Kit (JDK) installation. This project assumes Java version **17**.
- A [Telegram](https://telegram.org/) account.

#### Optional:

- [A full installation of Maven](https://www.baeldung.com/install-maven-on-windows-linux-mac).
- An IDE tailored for Java development, such as JetBrains IntelliJ. I used Visual Studio Code to develop this project.

### Setup instructions

#### I. Set up Telegram

1. If you haven't already, [download Telegram](https://telegram.org/) on your preferred device. Telegram is available for both PCs and mobile devices. Then, follow the setup instructions to create an account.
2. Follow the "**Getting Ready**" section in [this article](https://core.telegram.org/bots/tutorial) to create a new Telegram bot with BotFather. Note the resulting bot token.


#### II. Clone this project

3. Download the project into your location of choice, henceforth known as `location_of_project_download`. For example, if you are on macOS and you download the project into the `~/Downloads` folder, then the `location_of_project_download` is `~/Downloads`.
4. In the project root directory, create a `.env` file in the following format:

    ```
    USER=<user_name_of_your_bot_without_@>
    TOKEN=<bot_token_as_retrieved_from_BotFather>
    ```

    Do not enclose either value with quotes (`"`).

    **<span style="color:red">CAUTION: Do not commit your `.env` file to this repository or any other public repository, or expose your token in public. Doing so may allow others to control your bot without permission.</span>**

    There is an `application.yml` resource associated with the project, but it is configured to import the required values from the `.env` file.

5. From the command line, use the Maven wrapper to run the project:

    macOS or Linux:
    ```shell
    cd <location_of_project_download>/numberbot

    ./mvnw spring-boot:run
    ```

    Windows:
    ```cmd
    mvnw.cmd spring-boot:run
    ```

### Running tests

This project is being updated with unit tests on various components of the project.

Tests should be run from the project root directory. To run tests, navigate to the project root directory and run:

macOS or Linux:
```shell
./mvnw test
```

Windows:
```cmd
mvnw.cmd test
```