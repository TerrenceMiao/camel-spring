#!/bin/bash

version=$(java -version 2>&1 | sed 's/java version "\(.*\)\.\(.*\)\..*"/\1\2/; 1q')

if [[ "$version" -ge 18 ]]; then
    echo "Correct version JDK already installed"

    java -version
else
    echo "Install JDK 1.8 ..."

    cd /tmp

    # Get the latest Oracle JDK
    wget --no-cookies --header "Cookie: gpw_e24=xxx; oraclelicense=accept-securebackup-cookie;" http://download.oracle.com/otn-pub/java/jdk/8u72-b15/jdk-8u72-linux-x64.tar.gz

    # Install Oracle JDK
    sudo rm -rf /usr/local/jdk1.8.0_72
    sudo tar xvfz jdk-8u72-linux-x64.tar.gz -C /usr/local

    # Create alternative for Oracle JDK
    sudo /usr/sbin/alternatives --install /usr/bin/java java /usr/local/jdk1.8.0_72/bin/java 20000

    # Verify if change in SDK was done
    java -version
fi

exit 0