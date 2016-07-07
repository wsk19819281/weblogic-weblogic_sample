startEdit()

cd('/')
cmo.createJMSSystemResource('ExceptionHandlingModule')

cd('/SystemResources/ExceptionHandlingModule')
set('Targets',jarray.array([ObjectName('com.bea:Name=AdminServer,Type=Server')], ObjectName))
cmo.createSubDeployment('maersk.integration.ExceptionHandlingSubdeployment')

cd('/JMSSystemResources/ExceptionHandlingModule/JMSResource/ExceptionHandlingModule')
cmo.createConnectionFactory('maersk.integration.QueueConnectionFactory')

cd('/JMSSystemResources/ExceptionHandlingModule/JMSResource/ExceptionHandlingModule/ConnectionFactories/maersk.integration.QueueConnectionFactory')
cmo.setJNDIName('maersk.integration.QueueConnectionFactory')

cd('/JMSSystemResources/ExceptionHandlingModule/JMSResource/ExceptionHandlingModule/ConnectionFactories/maersk.integration.QueueConnectionFactory/SecurityParams/maersk.integration.QueueConnectionFactory')
cmo.setAttachJMSXUserId(false)

cd('/SystemResources/ExceptionHandlingModule/SubDeployments/maersk.integration.ExceptionHandlingSubdeployment')
set('Targets',jarray.array([], ObjectName))

cd('/JMSSystemResources/ExceptionHandlingModule/JMSResource/ExceptionHandlingModule/ConnectionFactories/maersk.integration.QueueConnectionFactory')
cmo.setSubDeploymentName('maersk.integration.ExceptionHandlingSubdeployment')

cd('/')
cmo.createFileStore('exceptionHandlingStore')

cd('/FileStores/exceptionHandlingStore')
set('Targets',jarray.array([ObjectName('com.bea:Name=AdminServer,Type=Server')], ObjectName))

cd('/')
cmo.createJMSServer('exceptionHandlingServer')

cd('/Deployments/exceptionHandlingServer')
cmo.setPersistentStore(getMBean('/FileStores/exceptionHandlingStore'))
set('Targets',jarray.array([ObjectName('com.bea:Name=AdminServer,Type=Server')], ObjectName))

cd('/SystemResources/ExceptionHandlingModule/SubDeployments/maersk.integration.ExceptionHandlingSubdeployment')
set('Targets',jarray.array([ObjectName('com.bea:Name=exceptionHandlingServer,Type=JMSServer')], ObjectName))

cd('/JMSSystemResources/ExceptionHandlingModule/JMSResource/ExceptionHandlingModule')
cmo.createQueue('maersk.integration.osbErrorQueue')

cd('/JMSSystemResources/ExceptionHandlingModule/JMSResource/ExceptionHandlingModule/Queues/maersk.integration.osbErrorQueue')
cmo.setJNDIName('maersk.integration.osbErrorQueue')
cmo.setSubDeploymentName('maersk.integration.ExceptionHandlingSubdeployment')

cd('/SystemResources/ExceptionHandlingModule/SubDeployments/maersk.integration.ExceptionHandlingSubdeployment')
set('Targets',jarray.array([ObjectName('com.bea:Name=exceptionHandlingServer,Type=JMSServer')], ObjectName))

activate()
