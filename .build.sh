#!/bin/sh

./gradlew clean
./gradlew build
sh ./.doxygen.sh
