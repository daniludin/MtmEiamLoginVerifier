"# MtmEiamLoginVerifier" 

Dieser Ant Custom Task pr�ft, ob ein bestimmter Suchbegriff auskommentiert ist,
wie z.B. die EIAM Login Configuration im applicationContext.xml

Das dist Target muss dazu um folgende Zeilen erweiter werden:

	<target name="dist" description="Create war">

		...
	
        <taskdef name="mtmEiamLoginVerifier" classname="ch.bgs.anttask.MtmEiamLoginVerifier" classpath="${basedir}/lib/MtmEiamLoginVerifier.jar"/>
        <mtmEiamLoginVerifier searchItem="saml"  fileToSearch="applicationContext.xml" />
	</target>
