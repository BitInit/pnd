#!/usr/bin/env bash

SCRIPT_DIR=$(dirname $0)
PND_HOME="$SCRIPT_DIR/.."

java -jar "$PND_HOME/lib/pnd-web.jar"