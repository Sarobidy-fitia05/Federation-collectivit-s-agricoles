# ActivitsApi

All URIs are relative to *https://api.federation-agricole.mg/v1*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**creerActivite**](ActivitsApi.md#creerActivite) | **POST** /activites | Créer une activité (collectivité ou fédération) |
| [**listerActivites**](ActivitsApi.md#listerActivites) | **GET** /activites | Lister les activités (avec filtres) |


<a id="creerActivite"></a>
# **creerActivite**
> Activite creerActivite(activiteInput)

Créer une activité (collectivité ou fédération)

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.ActivitsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.federation-agricole.mg/v1");

    ActivitsApi apiInstance = new ActivitsApi(defaultClient);
    ActiviteInput activiteInput = new ActiviteInput(); // ActiviteInput | 
    try {
      Activite result = apiInstance.creerActivite(activiteInput);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling ActivitsApi#creerActivite");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters

| Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **activiteInput** | [**ActiviteInput**](ActiviteInput.md)|  | |

### Return type

[**Activite**](Activite.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **201** | Activité créée |  -  |
| **400** | Données invalides |  -  |
| **404** | Entité introuvable |  -  |

<a id="listerActivites"></a>
# **listerActivites**
> List&lt;Activite&gt; listerActivites(entiteType, entiteId, typeActivite, obligatoire)

Lister les activités (avec filtres)

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.ActivitsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.federation-agricole.mg/v1");

    ActivitsApi apiInstance = new ActivitsApi(defaultClient);
    String entiteType = "collectivite"; // String | 
    Integer entiteId = 56; // Integer | 
    String typeActivite = "AG mensuelle"; // String | 
    Boolean obligatoire = true; // Boolean | 
    try {
      List<Activite> result = apiInstance.listerActivites(entiteType, entiteId, typeActivite, obligatoire);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling ActivitsApi#listerActivites");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters

| Name | Type | Description  | Notes |
|------------- | ------------- | ------------- | -------------|
| **entiteType** | **String**|  | [optional] [enum: collectivite, federation] |
| **entiteId** | **Integer**|  | [optional] |
| **typeActivite** | **String**|  | [optional] [enum: AG mensuelle, formation juniors, exceptionnelle, fédérale] |
| **obligatoire** | **Boolean**|  | [optional] |

### Return type

[**List&lt;Activite&gt;**](Activite.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Liste des activités |  -  |

