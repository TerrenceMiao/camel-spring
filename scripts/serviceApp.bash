#!/usr/bin/env bash

cp -f /tmp/camel-spring-1.0-SNAPSHOT.jar /var/tmp/camel-spring.jar

ln -fs /var/tmp/camel-spring.jar /etc/init.d/camel-spring
