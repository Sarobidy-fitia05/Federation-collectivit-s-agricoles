# MandatsApi

All URIs are relative to *https://api.federation-agricole.mg/v1*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**creerMandat**](MandatsApi.md#creerMandat) | **POST** /mandats | Créer un mandat (affecter un membre à un poste pour une période) |
| [**listerMandatsCollectivite**](MandatsApi.md#listerMandatsCollectivite) | **GET** /collectivites/{id_collectivite}/mandats | Lister les mandats d&#39;une collectivité |
| [**listerMandatsFederation**](MandatsApi.md#listerMandatsFederation) | **GET** /federation/mandats | Lister les mandats de la fédération |


<a id="creerMandat"></a>
# **creerMandat**
> Mandat creerMandat(mandatInput)

Créer un mandat (affecter un membre à un poste pour une période)

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.MandatsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.federation-agricole.mg/v1");

    MandatsApi apiInstance = new MandatsApi(defaultClient);
    MandatInput mandatInput = new MandatInput(); // MandatInput | 
    try {
      Mandat result = apiInstance.creerMandat(mandatInput);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling MandatsApi#creerMandat");
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
| **mandatInput** | [**MandatInput**](MandatInput.md)|  | |

### Return type

[**Mandat**](Mandat.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **201** | Mandat créé |  -  |
| **400** | Conditions non remplies. Exemples : - Poste déjà occupé sur ce mandat - Membre ayant déjà atteint 2 mandats sur ce poste - Poste de président de fédération réservé aux anciens/actuels présidents de collectivité  |  -  |
| **404** | Membre ou entité introuvable |  -  |
| **409** | Conflit de mandat (période ou poste déjà attribué) |  -  |

<a id="listerMandatsCollectivite"></a>
# **listerMandatsCollectivite**
> List&lt;Mandat&gt; listerMandatsCollectivite(idCollectivite, annee)

Lister les mandats d&#39;une collectivité

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.MandatsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.federation-agricole.mg/v1");

    MandatsApi apiInstance = new MandatsApi(defaultClient);
    Integer idCollectivite = 56; // Integer | 
    Integer annee = 2026; // Integer | 
    try {
      List<Mandat> result = apiInstance.listerMandatsCollectivite(idCollectivite, annee);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling MandatsApi#listerMandatsCollectivite");
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
| **annee** | **Integer**|  | [optional] |

### Return type

[**List&lt;Mandat&gt;**](Mandat.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Liste des mandats |  -  |
| **404** | Collectivité introuvable |  -  |

<a id="listerMandatsFederation"></a>
# **listerMandatsFederation**
> List&lt;Mandat&gt; listerMandatsFederation(annee)

Lister les mandats de la fédération

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.MandatsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.federation-agricole.mg/v1");

    MandatsApi apiInstance = new MandatsApi(defaultClient);
    Integer annee = 2026; // Integer | 
    try {
      List<Mandat> result = apiInstance.listerMandatsFederation(annee);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling MandatsApi#listerMandatsFederation");
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
| **annee** | **Integer**|  | [optional] |

### Return type

[**List&lt;Mandat&gt;**](Mandat.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Liste des mandats de la fédération |  -  |

