${name.getData()}
<#assign latitude = 0>
<#assign longitude = 0>
<#if (location.getData() != "")>
    <#assign geolocationJSONObject = jsonFactoryUtil.createJSONObject(location.getData())>
    <#assign latitude = geolocationJSONObject.getDouble("latitude")>
    <#assign longitude = geolocationJSONObject.getDouble("longitude")>
    <@liferay_map["map-display"] geolocation=true latitude=latitude longitude=longitude name="location${randomizer.nextInt()}" />
</#if>