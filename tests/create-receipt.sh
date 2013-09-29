#!/bin/sh

curl -s -v -X POST -d @receipt.json http://localhost:2010/receipt
