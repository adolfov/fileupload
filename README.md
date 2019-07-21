# FileUploader backend

Project skeleton generated with Spring Initializr
Simple backend for the ngFileUpload app: https://github.com/adolfov/ng-file-upload

Contains a REST Service that allows file uploads.
The service stores the file on the server's file system and the file's metadata in memory (h2)

## How to run

`./gradlew bootRun`

This will launch the server on http://localhost:8080

## How to run tests

run class FileuploadApplicationTests on your IDE

## Other notes

You can visualize the files that have been uploaded here: http://localhost:8080/files