"# MtmEiamLoginVerifier" 

Dieser Ant Custom Task prüft, ob ein bestimmter Suchbegriff auskommentiert ist,
wie z.B. die EIAM Login Configuration im applicationContext.xml
Dazu wird das fertige .war unzipped und durchsucht.

Das dist Target muss dazu um folgende Zeilen erweitert werden:

	<target name="dist" description="Create war">

		...
	
        <taskdef name="mtmEiamLoginVerifier" classname="ch.bgs.anttask.MtmEiamLoginVerifier" classpath="${basedir}/lib/MtmEiamLoginVerifier.jar"/>
        <mtmEiamLoginVerifier searchItem="saml"  fileToSearch="applicationContext.xml" />
	</target>
