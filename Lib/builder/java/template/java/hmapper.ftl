<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE hibernate-mapping PUBLIC
	"-//Hibernate/Hibernate Mapping DTD 3.0//EN"
	"http://hibernate.sourceforge.net/hibernate-mapping-3.0.dtd">

<hibernate-mapping package="${namespace}.model">
	<class name="${model}" table="${table.nameUpperCase}" dynamic-insert="true" dynamic-update="true">
<#list table.column as c>
	<#if c.key>
		<id name="${c.nameLowerCamel}" column="${c.nameUpperCase}" type="${c.typeLowerCamel}">
			<generator class="assigned" />
		</id>
	<#else>
		<property name="${c.nameLowerCamel}" column="${c.nameUpperCase}" type="${c.typeLowerCamel}" unique="false" length="${c.length}" not-null="true" />
	</#if>
</#list>
	</class>
</hibernate-mapping>
