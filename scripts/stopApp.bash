#!/bin/bash

isAppExist=$(pgrep java)

if [[ -n "$isAppExist" ]]; then
    kill "$isAppExist"
fi

exit 0