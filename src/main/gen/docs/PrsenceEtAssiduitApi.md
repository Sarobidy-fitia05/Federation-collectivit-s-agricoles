# PrsenceEtAssiduitApi

All URIs are relative to *https://api.federation-agricole.mg/v1*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**enregistrerPresences**](PrsenceEtAssiduitApi.md#enregistrerPresences) | **POST** /activites/{id_activite}/presences | Enregistrer la présence des membres à une activité |
| [**getFeuillePresence**](PrsenceEtAssiduitApi.md#getFeuillePresence) | **GET** /activites/{id_activite}/presences | Récupérer la feuille de présence d&#39;une activité |
| [**getTauxAssiduiteMembre**](PrsenceEtAssiduitApi.md#getTauxAssiduiteMembre) | **GET** /membres/{id_membre}/taux-assiduite | Calculer le taux d&#39;assiduité d&#39;un membre sur une période |


<a id="enregistrerPresences"></a>
# **enregistrerPresences**
> enregistrerPresences(idActivite, presenceInput)

Enregistrer la présence des membres à une activité

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.PrsenceEtAssiduitApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.federation-agricole.mg/v1");

    PrsenceEtAssiduitApi apiInstance = new PrsenceEtAssiduitApi(defaultClient);
    Integer idActivite = 56; // Integer | 
    List<PresenceInput> presenceInput = Arrays.asList(); // List<PresenceInput> | 
    try {
      apiInstance.enregistrerPresences(idActivite, presenceInput);
    } catch (ApiException e) {
      System.err.println("Exception when calling PrsenceEtAssiduitApi#enregistrerPresences");
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
| **idActivite** | **Integer**|  | |
| **presenceInput** | [**List&lt;PresenceInput&gt;**](PresenceInput.md)|  | |

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
| **200** | Présences enregistrées |  -  |
| **404** | Activité introuvable |  -  |

<a id="getFeuillePresence"></a>
# **getFeuillePresence**
> List&lt;Presence&gt; getFeuillePresence(idActivite)

Récupérer la feuille de présence d&#39;une activité

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.PrsenceEtAssiduitApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.federation-agricole.mg/v1");

    PrsenceEtAssiduitApi apiInstance = new PrsenceEtAssiduitApi(defaultClient);
    Integer idActivite = 56; // Integer | 
    try {
      List<Presence> result = apiInstance.getFeuillePresence(idActivite);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling PrsenceEtAssiduitApi#getFeuillePresence");
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
| **idActivite** | **Integer**|  | |

### Return type

[**List&lt;Presence&gt;**](Presence.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Liste des présences |  -  |
| **404** | Activité introuvable |  -  |

<a id="getTauxAssiduiteMembre"></a>
# **getTauxAssiduiteMembre**
> GetTauxAssiduiteMembre200Response getTauxAssiduiteMembre(idMembre, dateDebut, dateFin)

Calculer le taux d&#39;assiduité d&#39;un membre sur une période

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.PrsenceEtAssiduitApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.federation-agricole.mg/v1");

    PrsenceEtAssiduitApi apiInstance = new PrsenceEtAssiduitApi(defaultClient);
    Integer idMembre = 56; // Integer | 
    LocalDate dateDebut = LocalDate.now(); // LocalDate | 
    LocalDate dateFin = LocalDate.now(); // LocalDate | 
    try {
      GetTauxAssiduiteMembre200Response result = apiInstance.getTauxAssiduiteMembre(idMembre, dateDebut, dateFin);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling PrsenceEtAssiduitApi#getTauxAssiduiteMembre");
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
| **idMembre** | **Integer**|  | |
| **dateDebut** | **LocalDate**|  | |
| **dateFin** | **LocalDate**|  | |

### Return type

[**GetTauxAssiduiteMembre200Response**](GetTauxAssiduiteMembre200Response.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Taux d&#39;assiduité (en pourcentage, membres externes exclus) |  -  |
| **404** | Membre introuvable |  -  |

