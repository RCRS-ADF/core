#!/bin/sh

./gradlew clean
./gradlew build
mkdir -p build/docs/doxygen
doxygen
doxygen Doxyfile_ja
