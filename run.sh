#!/bin/zsh

if ! command -v java &> /dev/null; then
    exit 1
fi

java -XX:+ShowCodeDetailsInExceptionMessages -cp ./bin Main