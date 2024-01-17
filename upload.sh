#!/bin/bash

# Set the remote URL for origin
git remote set-url origin https://github.com/SoulForeverInPeace/Boot-PC

# Sleep for 1 second
sleep 1

# Additional commands or operations can be added here if needed
git fetch origin


sleep 1
git checkout -b main origin/main


sleep 1
git checkout -b master


sleep 1
git init
git add .


sleep 1
chmod +x removeFiles.sh
sh removeFiles.sh


sleep 1
git commit -m "Your commit message"

sleep 1
git push origin master
