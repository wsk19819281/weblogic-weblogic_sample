#Script to monitor running cluster
#Vernetto 2011-09-17

from java.io import File
from java.io import FileOutputStream
from java.io import FileInputStream
from java.util import HashMap
from java.util import HashSet
from java.util import ArrayList
from time import sleep

import datetime

import zlib
import zipfile
from os import *

configFileProperties = None

#=======================================================================================
# Utility function to load properties from a config file
#=======================================================================================

def loadProps(configPropFile):
    global configFileProperties
    propInputStream = FileInputStream(configPropFile)
    configFileProperties = Properties()
    configFileProperties.load(propInputStream)
    

def appendToAlarmLog(alarmmessage):
    alarmFile = open('clusteralarms.log', 'a')
    alarmFile.write(getNowTimestamp() + ' ' + alarmmessage + '\n')
    alarmFile.close()


def getNowTimestamp():
	now = datetime.datetime.now()
	nowtimestamp = now.strftime("%Y%m%dT%H%M%S")
	return nowtimestamp
	
def monitorServer():
    try:

		#initialize variables
		serverURL=configFileProperties.get('serverURL')
		serverName=configFileProperties.get('serverName')
		username=configFileProperties.get('username')
		password=configFileProperties.get('password')
		logFile=configFileProperties.get('logFile')
		aliveServerCountExpected=configFileProperties.get('aliveServerCountExpected')
		interval=configFileProperties.get('interval')

		#initialize timestamp to append to log filename
		nowtimestamp = getNowTimestamp()


		#save stdout handle 
		prev = theInterpreter.getOut()
		logFileNameWithTimestamp = logFile + '.' + nowtimestamp

		f = File(logFileNameWithTimestamp)
		fos = FileOutputStream(f)
		#set new stdout

		print "start the script"
		easeSyntax()

		
		connect(username, password, serverURL)

		serverRuntime()
		theInterpreter.setOut(fos)

		cd('/')
		print 'Health', cmo.getHealthState()

		cd('/ClusterRuntime/myCluster/')
		print 'AliveServerCount=', cmo.getAliveServerCount(), ' ServerNames', cmo.getServerNames()

		if (aliveServerCountExpected != cmo.getAliveServerCount()) :
		   alarmmessage = 'MONITORALARM, we were expecting AliveServerCount ' + aliveServerCountExpected + ' and we have instead %d , see file %s' % (cmo.getAliveServerCount() , logFileNameWithTimestamp, )
		   print alarmmessage 
		   appendToAlarmLog(alarmmessage)
		   
		   
		cd('/ClusterRuntime/myCluster/UnicastMessaging/UnicastMessagingRuntime')

		print 'DiscoveredGroupLeaders=',  cmo.getDiscoveredGroupLeaders(), ' Groups=', cmo.getGroups(), ' LocalGroupLeaderName=',  cmo.getLocalGroupLeaderName(), ' RemoteGroupsDiscoveredCount=',  cmo.getRemoteGroupsDiscoveredCount(), ' TotalGroupsCount=', cmo.getTotalGroupsCount()

		cd('/ServerChannelRuntimes/unicastChannel')
		print 'AcceptCount=' , cmo.getAcceptCount() , ' MessagesReceivedCount=' , cmo.getMessagesReceivedCount() , ' MessagesSentCount=' , cmo.getMessagesSentCount()
		scr = cmo.getServerConnectionRuntimes()
		#scr is an array of weblogic.server.channels.ServerConnectionRuntimeImpl$SerializableConnectionRuntime
		for myscr in scr:
			print "BytesReceivedCount=", myscr.getBytesReceivedCount(), " BytesSentCount=", myscr.getBytesSentCount(), " ConnectTime=", myscr.getConnectTime()," MessagesReceivedCount=",  myscr.getMessagesReceivedCount(), " MessagesSentCount=", myscr.getMessagesSentCount()

		print ""

		threadDump()


		cd('/JVMRuntime/' + serverName)
		#this is valid for JRockit
		#print "HeapFreeCurrent=", cmo.getHeapFreeCurrent(), " TotalGarbageCollectionTime", cmo.getTotalGarbageCollectionTime(), " TotalNumberOfThreads=", cmo.getTotalNumberOfThreads()
		#this is valid for JRockit
		print "HeapFreeCurrent=", cmo.getHeapFreeCurrent(), ' HeapSizeCurrent=', cmo.getHeapSizeCurrent()

		cd('/ThreadPoolRuntime/ThreadPoolRuntime')

		print 'HoggingThreadCount=', cmo.getHoggingThreadCount(), ' PendingUserRequestCount', cmo.getPendingUserRequestCount(), ' StandbyThreadCount' , cmo.getStandbyThreadCount()
		print 'CompletedRequestCount=', cmo.getCompletedRequestCount(), ' ExecuteThreadIdleCount=', cmo.getExecuteThreadIdleCount(), ' ExecuteThreadTotalCount=', cmo.getExecuteThreadTotalCount()

		#restore stdout
		theInterpreter.setOut(prev)

		fos.close()
		#now zip the report
		zipfileLog = zipfile.ZipFile(logFileNameWithTimestamp + '.zip', 'w')
		zipfileLog.write(logFileNameWithTimestamp, compress_type=zipfile.ZIP_DEFLATED)
		zipfileLog.close()
		os.remove(logFileNameWithTimestamp)

		
    except:
        print "Unexpected error:", sys.exc_info()[0]
		fos.close()
        raise
		

		
		

# monitor script init
try:
    # sys.argv[1] is the config properties file
	configFile = 'c:\pierre\morrison\clustermonitor.properties'
	print 'Loading config from :', configFile
	loadProps(configFile)
	interval = configFileProperties.getProperty('interval')

	while True:
		try:
			sleep(float(interval))
			monitorServer()
		except:
			appendToAlarmLog('error querying server')
			dumpStack()


except:
    print "Unexpected error: ", sys.exc_info()[0]
    dumpStack()
    raise
	
