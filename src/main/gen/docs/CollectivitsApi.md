# CollectivitsApi

All URIs are relative to *https://api.federation-agricole.mg/v1*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**autoriserCollectivite**](CollectivitsApi.md#autoriserCollectivite) | **PUT** /collectivites/{id_collectivite}/autorisation | Accorder l&#39;autorisation d&#39;ouverture (fédération) |
| [**createCollectivite**](CollectivitsApi.md#createCollectivite) | **POST** /collectivites | Créer une nouvelle collectivité (sous conditions) |


<a id="autoriserCollectivite"></a>
# **autoriserCollectivite**
> autoriserCollectivite(idCollectivite, autoriserCollectiviteRequest)

Accorder l&#39;autorisation d&#39;ouverture (fédération)

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.CollectivitsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.federation-agricole.mg/v1");

    CollectivitsApi apiInstance = new CollectivitsApi(defaultClient);
    Integer idCollectivite = 56; // Integer | 
    AutoriserCollectiviteRequest autoriserCollectiviteRequest = new AutoriserCollectiviteRequest(); // AutoriserCollectiviteRequest | 
    try {
      apiInstance.autoriserCollectivite(idCollectivite, autoriserCollectiviteRequest);
    } catch (ApiException e) {
      System.err.println("Exception when calling CollectivitsApi#autoriserCollectivite");
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
| **idCollectivite** | **Integer**|  | |
| **autoriserCollectiviteRequest** | [**AutoriserCollectiviteRequest**](AutoriserCollectiviteRequest.md)|  | |

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: Not defined

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Autorisation mise à jour |  -  |
| **404** | Collectivité non trouvée |  -  |

<a id="createCollectivite"></a>
# **createCollectivite**
> CollectiviteResponse createCollectivite(createCollectiviteRequest)

Créer une nouvelle collectivité (sous conditions)

Crée une collectivité si les conditions sont remplies : - Au moins 10 membres inscrits (dont 5 avec antériorité ≥ 6 mois) - Postes spécifiques occupés - Autorisation de la fédération (à obtenir via un endpoint dédié) 

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.CollectivitsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.federation-agricole.mg/v1");

    CollectivitsApi apiInstance = new CollectivitsApi(defaultClient);
    CreateCollectiviteRequest createCollectiviteRequest = new CreateCollectiviteRequest(); // CreateCollectiviteRequest | 
    try {
      CollectiviteResponse result = apiInstance.createCollectivite(createCollectiviteRequest);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling CollectivitsApi#createCollectivite");
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
| **createCollectiviteRequest** | [**CreateCollectiviteRequest**](CreateCollectiviteRequest.md)|  | |

### Return type

[**CollectiviteResponse**](CollectiviteResponse.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **201** | Collectivité créée (autorisation en attente) |  -  |
| **400** | Conditions non remplies (message d&#39;erreur détaillé) |  -  |
| **403** | Autorisation de la fédération refusée |  -  |
| **409** | Numéro unique ou nom déjà utilisé |  -  |

