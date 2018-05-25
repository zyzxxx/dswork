<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper PUBLIC
	"-//ibatis.apache.org//DTD Mapper 3.0//EN" 
	"http://ibatis.apache.org/dtd/ibatis-3-mapper.dtd">

<mapper namespace="${namespace}.model.${model}">

<insert id="insert" parameterType="${namespace}.model.${model}">
	insert into ${table.name}
	(<#list columnList as c>${c.name}<#if c_has_next>, </#if></#list>)
	values
	(<#list columnList as c>${'#'}{${c.nameLowerCamel}}<#if c_has_next>, </#if></#list>)
</insert>

<delete id="delete">
	delete from ${table.name} where ID=${'#'}{id}
</delete>

<update id="update" parameterType="${namespace}.model.${model}">
	update ${table.name} set
<#list columnList as c>
	<#if !c.key>
		${c.name}=${'#'}{${c.nameLowerCamel}}<#if c_has_next>,</#if>
	</#if>
</#list>
	where ID=${'#'}{id}
</update>

<resultMap id="result" type="${namespace}.model.${model}">
<#list columnList as c>
	<#if c.key>
	<id property="${c.nameLowerCamel}" column="${c.name}" />
	<#else>
	<result property="${c.nameLowerCamel}" column="${c.name}" />
	</#if>
</#list>
</resultMap>

<sql id="columns"><#list columnList as c>${c.name}<#if c_has_next>, </#if></#list></sql>

<sql id="dynamicWhere">
	<where>
<#list columnList as c>
	<#if c.key>
		<if test="@Ognl@isNotEmpty(${c.nameLowerCamel})"> and ${c.name}=${'#'}{id} </if>
	<#else>
		<if test="@Ognl@isNotEmpty(${c.nameLowerCamel})"> and ${c.name} like ${'#'}{${c.nameLowerCamel}, typeHandler=LikeTypeHandler} </if>
	</#if>
</#list>
	</where>
</sql>

<select id="select" resultMap="result">
	select <include refid="columns" /> from ${table.name} where ID=${'#'}{id}
</select>

<select id="query" resultMap="result">
	select <include refid="common.top" /><include refid="columns" /> from ${table.name}
	<include refid="dynamicWhere" />
</select>

<select id="queryCount" resultType="int">
	select count(1) from ${table.name}
	<include refid="dynamicWhere" />
</select>

</mapper>
