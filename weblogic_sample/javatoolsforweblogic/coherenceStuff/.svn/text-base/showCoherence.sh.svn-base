#!/bin/bash

echo "pid miminheap maxheap vsize residnt share text data name jvm"
ps -ef | grep coh- | grep java | grep -v grep | while read LINE
do
        MINHEAP=$(echo $LINE | sed 's/.*-Xms\([^ ]*\).*/\1/')
        MAXHEAP=$(echo $LINE | sed 's/.*-Xmx\([^ ]*\).*/\1/')
        PID=$(echo $LINE | awk '{print $2}')
        JVM=$(echo $LINE | awk '{print $8}')

        NAME=$(echo $LINE | sed -n -e 's/.*com.maersk.cache.server.\([^ ]*\).*/\1/p')
        if [ -z "$NAME" ] ; then
                NAME=$(echo $LINE | sed -n -e 's/.*-Dtangosol.coherence.role=\([^ ]*\).*/\1/p')
        fi
        TYPE=$(echo $LINE | sed -n -e 's/.*\/maerskwas\/coherence\/\(.*\)\/etc.*/\1/p')
	cat /proc/$PID/statm | \
        awk -vpid=$PID \
        -vjvm="$JVM" \
        -vname="$NAME" \
        -vminheap=$MINHEAP \
        -vmaxheap=$MAXHEAP \
	-vtype=$TYPE \
        '{ printf ("%ld\t%s\t%s\t%ldmb\t%ldmb\t%ldmb\t%ldmb\t%ldmb\t%s\t%s\t%s\n",
        pid, minheap, maxheap,
        ($1 * 4) / 1024,
        ($2 * 4) / 1024,
        ($3 * 4) / 1024,
        $(4 * 4)/1024, ($6 * 4)/1024, type, name, jvm); }'

done
