--------------------------------------------------------
--  File created - Sunday-April-29-2012  
--------------------------------------------------------
  DROP TABLE "ACME_DATASOURCES";
  DROP TABLE "ACME_DATASOURCES_ENV";
  DROP TABLE "ACME_DOMAINS";
  DROP TABLE "ACME_ENVS";
  DROP TABLE "ACME_FILESYSTEMS";
  DROP TABLE "ACME_JMSMODULES";
  DROP TABLE "ACME_JMSQUEUES";
  DROP TABLE "ACME_MACHINES";
  DROP TABLE "ACME_NIC";
  DROP TABLE "ACME_OSBPROJECTS";
  DROP TABLE "ACME_PROJECTS";
  DROP TABLE "ACME_SERVERS";
--------------------------------------------------------
--  DDL for Table ACME_DATASOURCES
--------------------------------------------------------

  CREATE TABLE "ACME_DATASOURCES"
   (    "DSNAME" VARCHAR2(40 BYTE),
    "DSJNDINAME" VARCHAR2(50 BYTE)
   ) ;
--------------------------------------------------------
--  DDL for Table ACME_DATASOURCES_ENV
--------------------------------------------------------

  CREATE TABLE "ACME_DATASOURCES_ENV"
   (    "DSNAME" VARCHAR2(40 BYTE),
    "ENVNAME" VARCHAR2(20 BYTE),
    "URL" VARCHAR2(100 BYTE),
    "USERNAME" VARCHAR2(20 BYTE)
   ) ;
--------------------------------------------------------
--  DDL for Table ACME_DOMAINS
--------------------------------------------------------

  CREATE TABLE "ACME_DOMAINS"
   (    "DOMAINNAME" VARCHAR2(20 BYTE),
    "ENV" VARCHAR2(20 BYTE),
    "DOMAINTYPE" VARCHAR2(20 BYTE),
    "CLUSTERNAME" VARCHAR2(20 BYTE)
   ) ;
--------------------------------------------------------
--  DDL for Table ACME_ENVS
--------------------------------------------------------

  CREATE TABLE "ACME_ENVS"
   (    "ENVNAME" VARCHAR2(20 BYTE)
   ) ;
--------------------------------------------------------
--  DDL for Table ACME_FILESYSTEMS
--------------------------------------------------------

  CREATE TABLE "ACME_FILESYSTEMS"
   (    "NAME" VARCHAR2(100 BYTE),
    "ENVNAME" VARCHAR2(20 BYTE),
    "TYPE" VARCHAR2(20 BYTE),
    "OSBPROJECTS" VARCHAR2(100 BYTE)
   ) ;
--------------------------------------------------------
--  DDL for Table ACME_JMSMODULES
--------------------------------------------------------

  CREATE TABLE "ACME_JMSMODULES"
   (    "JMSMODULENAME" VARCHAR2(40 BYTE),
    "PROJECTNAME" VARCHAR2(20 BYTE)
   ) ;
--------------------------------------------------------
--  DDL for Table ACME_JMSQUEUES
--------------------------------------------------------

  CREATE TABLE "ACME_JMSQUEUES"
   (    "JMSNAME" VARCHAR2(40 BYTE),
    "JNDINAME" VARCHAR2(100 BYTE),
    "REPROCESSQUEUE" VARCHAR2(40 BYTE),
    "JMSMODULENAME" VARCHAR2(40 BYTE),
    "ISERRORQUEUE" NUMBER(1,0)
   ) ;
--------------------------------------------------------
--  DDL for Table ACME_MACHINES
--------------------------------------------------------

  CREATE TABLE "ACME_MACHINES"
   (    "MACHINENAME" VARCHAR2(20 BYTE),
    "IPLISTENADDRESS" VARCHAR2(20 BYTE),
    "VIP" VARCHAR2(40 BYTE),
    "ENV" VARCHAR2(20 BYTE)
   ) ;
--------------------------------------------------------
--  DDL for Table ACME_NIC
--------------------------------------------------------

  CREATE TABLE "ACME_NIC"
   (    "NICNAME" VARCHAR2(20 BYTE),
    "MACHINE" VARCHAR2(20 BYTE),
    "IP" VARCHAR2(20 BYTE)
   ) ;
--------------------------------------------------------
--  DDL for Table ACME_OSBPROJECTS
--------------------------------------------------------

  CREATE TABLE "ACME_OSBPROJECTS"
   (    "OSBPROJECTNAME" VARCHAR2(50 BYTE)
   ) ;
--------------------------------------------------------
--  DDL for Table ACME_PROJECTS
--------------------------------------------------------

  CREATE TABLE "ACME_PROJECTS"
   (    "PROJECTNAME" VARCHAR2(20 BYTE)
   ) ;
--------------------------------------------------------
--  DDL for Table ACME_SERVERS
--------------------------------------------------------

  CREATE TABLE "ACME_SERVERS"
   (    "ENV" VARCHAR2(20 BYTE),
    "SERVERNAME" VARCHAR2(20 BYTE),
    "ISADMIN" NUMBER(1,0),
    "MACHINENAME" VARCHAR2(20 BYTE),
    "PORT" VARCHAR2(5 BYTE)
   ) ;
--------------------------------------------------------
--  DDL for Index ACME_DATASOURCES_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "ACME_DATASOURCES_PK" ON "ACME_DATASOURCES" ("DSNAME")
  ;
--------------------------------------------------------
--  DDL for Index ACME_DATASOURCES_ENV_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "ACME_DATASOURCES_ENV_PK" ON "ACME_DATASOURCES_ENV" ("DSNAME", "ENVNAME")
  ;
--------------------------------------------------------
--  DDL for Index ACME_DOMAINS_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "ACME_DOMAINS_PK" ON "ACME_DOMAINS" ("ENV")
  ;
--------------------------------------------------------
--  DDL for Index ACME_DOMAINS_UK1
--------------------------------------------------------

  CREATE UNIQUE INDEX "ACME_DOMAINS_UK1" ON "ACME_DOMAINS" ("DOMAINNAME")
  ;
--------------------------------------------------------
--  DDL for Index ACME_ENVS_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "ACME_ENVS_PK" ON "ACME_ENVS" ("ENVNAME")
  ;
--------------------------------------------------------
--  DDL for Index ACME_FILESYSTEMS_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "ACME_FILESYSTEMS_PK" ON "ACME_FILESYSTEMS" ("NAME")
  ;
--------------------------------------------------------
--  DDL for Index ACME_JMSMODULES_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "ACME_JMSMODULES_PK" ON "ACME_JMSMODULES" ("JMSMODULENAME")
  ;
--------------------------------------------------------
--  DDL for Index ACME_JMSQUEUES_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "ACME_JMSQUEUES_PK" ON "ACME_JMSQUEUES" ("JMSNAME")
  ;
--------------------------------------------------------
--  DDL for Index ACME_JMSQUEUES_UK1
--------------------------------------------------------

  CREATE UNIQUE INDEX "ACME_JMSQUEUES_UK1" ON "ACME_JMSQUEUES" ("JNDINAME")
  ;
--------------------------------------------------------
--  DDL for Index ACME_MACHINES_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "ACME_MACHINES_PK" ON "ACME_MACHINES" ("MACHINENAME")
  ;
--------------------------------------------------------
--  DDL for Index ACME_NIC_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "ACME_NIC_PK" ON "ACME_NIC" ("NICNAME", "MACHINE")
  ;
--------------------------------------------------------
--  DDL for Index ACME_OSBPROJECTS_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "ACME_OSBPROJECTS_PK" ON "ACME_OSBPROJECTS" ("OSBPROJECTNAME")
  ;
--------------------------------------------------------
--  DDL for Index ACME_PROJECTS_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "ACME_PROJECTS_PK" ON "ACME_PROJECTS" ("PROJECTNAME")
  ;
--------------------------------------------------------
--  DDL for Index ACME_SERVERS_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "ACME_SERVERS_PK" ON "ACME_SERVERS" ("SERVERNAME")
  ;
--------------------------------------------------------
--  Constraints for Table ACME_DATASOURCES
--------------------------------------------------------

  ALTER TABLE "ACME_DATASOURCES" ADD CONSTRAINT "ACME_DATASOURCES_PK" PRIMARY KEY ("DSNAME") ENABLE;
  ALTER TABLE "ACME_DATASOURCES" MODIFY ("DSNAME" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table ACME_DATASOURCES_ENV
--------------------------------------------------------

  ALTER TABLE "ACME_DATASOURCES_ENV" ADD CONSTRAINT "ACME_DATASOURCES_ENV_PK" PRIMARY KEY ("DSNAME", "ENVNAME") ENABLE;
  ALTER TABLE "ACME_DATASOURCES_ENV" MODIFY ("URL" NOT NULL ENABLE);
  ALTER TABLE "ACME_DATASOURCES_ENV" MODIFY ("ENVNAME" NOT NULL ENABLE);
  ALTER TABLE "ACME_DATASOURCES_ENV" MODIFY ("DSNAME" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table ACME_DOMAINS
--------------------------------------------------------

  ALTER TABLE "ACME_DOMAINS" ADD CONSTRAINT "ACME_DOMAINS_UK1" UNIQUE ("DOMAINNAME") ENABLE;
  ALTER TABLE "ACME_DOMAINS" ADD CONSTRAINT "ACME_DOMAINS_PK" PRIMARY KEY ("ENV") ENABLE;
  ALTER TABLE "ACME_DOMAINS" MODIFY ("ENV" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table ACME_ENVS
--------------------------------------------------------

  ALTER TABLE "ACME_ENVS" ADD CONSTRAINT "ACME_ENVS_PK" PRIMARY KEY ("ENVNAME") ENABLE;
  ALTER TABLE "ACME_ENVS" MODIFY ("ENVNAME" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table ACME_FILESYSTEMS
--------------------------------------------------------

  ALTER TABLE "ACME_FILESYSTEMS" ADD CONSTRAINT "ACME_FILESYSTEMS_PK" PRIMARY KEY ("NAME") ENABLE;
  ALTER TABLE "ACME_FILESYSTEMS" MODIFY ("ENVNAME" NOT NULL ENABLE);
  ALTER TABLE "ACME_FILESYSTEMS" MODIFY ("NAME" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table ACME_JMSMODULES
--------------------------------------------------------

  ALTER TABLE "ACME_JMSMODULES" MODIFY ("PROJECTNAME" NOT NULL ENABLE);
  ALTER TABLE "ACME_JMSMODULES" ADD CONSTRAINT "ACME_JMSMODULES_PK" PRIMARY KEY ("JMSMODULENAME") ENABLE;
  ALTER TABLE "ACME_JMSMODULES" MODIFY ("JMSMODULENAME" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table ACME_JMSQUEUES
--------------------------------------------------------

  ALTER TABLE "ACME_JMSQUEUES" ADD CONSTRAINT "ACME_JMSQUEUES_UK1" UNIQUE ("JNDINAME") ENABLE;
  ALTER TABLE "ACME_JMSQUEUES" ADD CONSTRAINT "ACME_JMSQUEUES_PK" PRIMARY KEY ("JMSNAME") ENABLE;
  ALTER TABLE "ACME_JMSQUEUES" MODIFY ("JMSNAME" NOT NULL ENABLE);
  ALTER TABLE "ACME_JMSQUEUES" MODIFY ("JMSMODULENAME" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table ACME_MACHINES
--------------------------------------------------------

  ALTER TABLE "ACME_MACHINES" ADD CONSTRAINT "ACME_MACHINES_PK" PRIMARY KEY ("MACHINENAME") ENABLE;
  ALTER TABLE "ACME_MACHINES" MODIFY ("MACHINENAME" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table ACME_NIC
--------------------------------------------------------

  ALTER TABLE "ACME_NIC" ADD CONSTRAINT "ACME_NIC_PK" PRIMARY KEY ("NICNAME", "MACHINE") ENABLE;
  ALTER TABLE "ACME_NIC" MODIFY ("MACHINE" NOT NULL ENABLE);
  ALTER TABLE "ACME_NIC" MODIFY ("NICNAME" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table ACME_OSBPROJECTS
--------------------------------------------------------

  ALTER TABLE "ACME_OSBPROJECTS" ADD CONSTRAINT "ACME_OSBPROJECTS_PK" PRIMARY KEY ("OSBPROJECTNAME") ENABLE;
  ALTER TABLE "ACME_OSBPROJECTS" MODIFY ("OSBPROJECTNAME" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table ACME_PROJECTS
--------------------------------------------------------

  ALTER TABLE "ACME_PROJECTS" ADD CONSTRAINT "ACME_PROJECTS_PK" PRIMARY KEY ("PROJECTNAME") ENABLE;
  ALTER TABLE "ACME_PROJECTS" MODIFY ("PROJECTNAME" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table ACME_SERVERS
--------------------------------------------------------

  ALTER TABLE "ACME_SERVERS" ADD CONSTRAINT "ACME_SERVERS_PK" PRIMARY KEY ("SERVERNAME") ENABLE;
  ALTER TABLE "ACME_SERVERS" MODIFY ("MACHINENAME" NOT NULL ENABLE);
  ALTER TABLE "ACME_SERVERS" MODIFY ("ISADMIN" NOT NULL ENABLE);
  ALTER TABLE "ACME_SERVERS" MODIFY ("SERVERNAME" NOT NULL ENABLE);
--------------------------------------------------------
--  Ref Constraints for Table ACME_JMSMODULES
--------------------------------------------------------

  ALTER TABLE "ACME_JMSMODULES" ADD CONSTRAINT "ACME_JMSMODULES_ACME__FK1" FOREIGN KEY ("PROJECTNAME")
      REFERENCES "ACME_PROJECTS" ("PROJECTNAME") ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table ACME_JMSQUEUES
--------------------------------------------------------

  ALTER TABLE "ACME_JMSQUEUES" ADD CONSTRAINT "ACME_JMSQUEUES_ACME_J_FK1" FOREIGN KEY ("JMSMODULENAME")
      REFERENCES "ACME_JMSMODULES" ("JMSMODULENAME") ENABLE;
  ALTER TABLE "ACME_JMSQUEUES" ADD CONSTRAINT "ACME_JMSQUEUES_ACME_J_FK2" FOREIGN KEY ("REPROCESSQUEUE")
      REFERENCES "ACME_JMSQUEUES" ("JMSNAME") ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table ACME_SERVERS
--------------------------------------------------------

  ALTER TABLE "ACME_SERVERS" ADD CONSTRAINT "ACME_SERVERS_ACME_MAC_FK1" FOREIGN KEY ("MACHINENAME")
      REFERENCES "ACME_MACHINES" ("MACHINENAME") ENABLE;
