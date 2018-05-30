package com.cloudrain.dercho.foundation;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.junit.Test;

import java.util.Map;

/**
 * Created by lugan on 5/13/2016.
 */
public class JsonTest {
    @Test
    public void jsonTest() {
        String json = "{\"LocationExpression\":\"1\",\"OrganizationExpression\":\"1\",\"ContractExpression\":\"1\",\"CompanyExpression\":\"1\",\"BrandExpression\":\"1\",\"ServiceDefinitionExpression\":\"1\",\"ActualServiceExpression\":\"1\",\"DeviceExpression\":\"1\",\"IPAExpression\":\"1\",\"LicenseExpression\":\"1\",\"LocationDebit\":true,\"LocationCredit\":true,\"OrganizationCredit\":true,\"OrganizationDebit\":true,\"ContractDebit\":true,\"CompanyCredit\":true,\"BrandDebit\":true,\"ServiceDefinitionCredit\":true,\"ActualServiceDebit\":true,\"DeviceCredit\":true,\"IPADebit\":true,\"LicenseCredit\":true}";
        Map<String, Object> a = new Gson().fromJson(json, Map.class);
        a.forEach((x,y)->System.out.println(x+y));
    }
}
