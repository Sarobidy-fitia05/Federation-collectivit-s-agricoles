# CotisationsApi

All URIs are relative to *https://api.federation-agricole.mg/v1*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**enregistrerCotisation**](CotisationsApi.md#enregistrerCotisation) | **POST** /cotisations | Enregistrer une cotisation (encaissement) |
| [**getPourcentageReversement**](CotisationsApi.md#getPourcentageReversement) | **GET** /federation/reversement | Obtenir le pourcentage de reversement des cotisations périodiques à la fédération |
| [**listerCotisationsParCollectivite**](CotisationsApi.md#listerCotisationsParCollectivite) | **GET** /collectivites/{id_collectivite}/cotisations | Liste des cotisations d&#39;une collectivité |


<a id="enregistrerCotisation"></a>
# **enregistrerCotisation**
> Cotisation enregistrerCotisation(cotisationInput)

Enregistrer une cotisation (encaissement)

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.CotisationsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.federation-agricole.mg/v1");

    CotisationsApi apiInstance = new CotisationsApi(defaultClient);
    CotisationInput cotisationInput = new CotisationInput(); // CotisationInput | 
    try {
      Cotisation result = apiInstance.enregistrerCotisation(cotisationInput);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling CotisationsApi#enregistrerCotisation");
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
| **cotisationInput** | [**CotisationInput**](CotisationInput.md)|  | |

### Return type

[**Cotisation**](Cotisation.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **201** | Cotisation enregistrée |  -  |
| **404** | Membre ou collectivité non trouvé |  -  |

<a id="getPourcentageReversement"></a>
# **getPourcentageReversement**
> GetPourcentageReversement200Response getPourcentageReversement()

Obtenir le pourcentage de reversement des cotisations périodiques à la fédération

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.CotisationsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.federation-agricole.mg/v1");

    CotisationsApi apiInstance = new CotisationsApi(defaultClient);
    try {
      GetPourcentageReversement200Response result = apiInstance.getPourcentageReversement();
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling CotisationsApi#getPourcentageReversement");
      System.err.println("Status code: " + e.getCode());
      System.err.println("Reason: " + e.getResponseBody());
      System.err.println("Response headers: " + e.getResponseHeaders());
      e.printStackTrace();
    }
  }
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**GetPourcentageReversement200Response**](GetPourcentageReversement200Response.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Pourcentage actuel |  -  |

<a id="listerCotisationsParCollectivite"></a>
# **listerCotisationsParCollectivite**
> List&lt;Cotisation&gt; listerCotisationsParCollectivite(idCollectivite, periode)

Liste des cotisations d&#39;une collectivité

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.CotisationsApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.federation-agricole.mg/v1");

    CotisationsApi apiInstance = new CotisationsApi(defaultClient);
    Integer idCollectivite = 56; // Integer | 
    String periode = "mensuelle"; // String | 
    try {
      List<Cotisation> result = apiInstance.listerCotisationsParCollectivite(idCollectivite, periode);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling CotisationsApi#listerCotisationsParCollectivite");
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
| **periode** | **String**|  | [optional] [enum: mensuelle, annuelle, ponctuelle] |

### Return type

[**List&lt;Cotisation&gt;**](Cotisation.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Liste des cotisations |  -  |
| **404** | Collectivité introuvable |  -  |

