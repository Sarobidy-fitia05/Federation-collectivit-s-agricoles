# StatistiquesApi

All URIs are relative to *https://api.federation-agricole.mg/v1*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**getRapportAnnuelFederation**](StatistiquesApi.md#getRapportAnnuelFederation) | **GET** /federation/statistiques-annuelles | Rapport annuel global de la fédération (toutes collectivités) |
| [**getRapportMensuelCollectivite**](StatistiquesApi.md#getRapportMensuelCollectivite) | **GET** /collectivites/{id_collectivite}/statistiques-mensuelles | Rapport mensuel du président à la fédération |
| [**getRapportMensuelFederation**](StatistiquesApi.md#getRapportMensuelFederation) | **GET** /federation/statistiques-mensuelles | Rapport mensuel global de la fédération (toutes collectivités) |
| [**getRapportPeriodeFederation**](StatistiquesApi.md#getRapportPeriodeFederation) | **GET** /federation/statistiques-periode | Rapport de la fédération sur une période libre |
| [**getStatistiquesMembreCollectivite**](StatistiquesApi.md#getStatistiquesMembreCollectivite) | **GET** /collectivites/{id_collectivite}/statistiques-membres | Statistiques détaillées par membre actif |


<a id="getRapportAnnuelFederation"></a>
# **getRapportAnnuelFederation**
> RapportAnnuelFederation getRapportAnnuelFederation(annee)

Rapport annuel global de la fédération (toutes collectivités)

Rapport présenté lors des assemblées générales annuelles. Agrège pour chaque collectivité, sur l&#39;année civile donnée : - Le taux d&#39;assiduité global - Le pourcentage de membres à jour dans leurs cotisations - Le nombre de nouveaux adhérents 

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.StatistiquesApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.federation-agricole.mg/v1");

    StatistiquesApi apiInstance = new StatistiquesApi(defaultClient);
    Integer annee = 2026; // Integer | 
    try {
      RapportAnnuelFederation result = apiInstance.getRapportAnnuelFederation(annee);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling StatistiquesApi#getRapportAnnuelFederation");
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
| **annee** | **Integer**|  | |

### Return type

[**RapportAnnuelFederation**](RapportAnnuelFederation.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Rapport annuel global par collectivité |  -  |

<a id="getRapportMensuelCollectivite"></a>
# **getRapportMensuelCollectivite**
> GetRapportMensuelCollectivite200Response getRapportMensuelCollectivite(idCollectivite, mois)

Rapport mensuel du président à la fédération

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.StatistiquesApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.federation-agricole.mg/v1");

    StatistiquesApi apiInstance = new StatistiquesApi(defaultClient);
    Integer idCollectivite = 56; // Integer | 
    LocalDate mois = LocalDate.parse("Wed Apr 01 03:00:00 EAT 2026"); // LocalDate | 
    try {
      GetRapportMensuelCollectivite200Response result = apiInstance.getRapportMensuelCollectivite(idCollectivite, mois);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling StatistiquesApi#getRapportMensuelCollectivite");
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
| **mois** | **LocalDate**|  | |

### Return type

[**GetRapportMensuelCollectivite200Response**](GetRapportMensuelCollectivite200Response.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Taux d&#39;assiduité globale et nombre de membres actifs |  -  |
| **404** | Collectivité introuvable |  -  |

<a id="getRapportMensuelFederation"></a>
# **getRapportMensuelFederation**
> RapportMensuelFederation getRapportMensuelFederation(mois)

Rapport mensuel global de la fédération (toutes collectivités)

Rapport établi par le secrétaire de la fédération. Agrège pour chaque collectivité, sur le mois donné : - Le taux d&#39;assiduité global - Le pourcentage de membres à jour dans leurs cotisations - Le nombre de nouveaux adhérents 

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.StatistiquesApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.federation-agricole.mg/v1");

    StatistiquesApi apiInstance = new StatistiquesApi(defaultClient);
    LocalDate mois = LocalDate.parse("Wed Apr 01 03:00:00 EAT 2026"); // LocalDate | 
    try {
      RapportMensuelFederation result = apiInstance.getRapportMensuelFederation(mois);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling StatistiquesApi#getRapportMensuelFederation");
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
| **mois** | **LocalDate**|  | |

### Return type

[**RapportMensuelFederation**](RapportMensuelFederation.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Rapport mensuel global par collectivité |  -  |

<a id="getRapportPeriodeFederation"></a>
# **getRapportPeriodeFederation**
> List&lt;StatistiqueCollectivitePeriode&gt; getRapportPeriodeFederation(dateDebut, dateFin)

Rapport de la fédération sur une période libre

Permet au secrétaire d&#39;interroger les statistiques sur n&#39;importe quelle période, par collectivité : - Taux d&#39;assiduité global - Pourcentage de membres à jour dans leurs cotisations - Nombre de nouveaux adhérents 

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.StatistiquesApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.federation-agricole.mg/v1");

    StatistiquesApi apiInstance = new StatistiquesApi(defaultClient);
    LocalDate dateDebut = LocalDate.now(); // LocalDate | 
    LocalDate dateFin = LocalDate.now(); // LocalDate | 
    try {
      List<StatistiqueCollectivitePeriode> result = apiInstance.getRapportPeriodeFederation(dateDebut, dateFin);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling StatistiquesApi#getRapportPeriodeFederation");
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
| **dateDebut** | **LocalDate**|  | |
| **dateFin** | **LocalDate**|  | |

### Return type

[**List&lt;StatistiqueCollectivitePeriode&gt;**](StatistiqueCollectivitePeriode.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Rapport par collectivité sur la période donnée |  -  |

<a id="getStatistiquesMembreCollectivite"></a>
# **getStatistiquesMembreCollectivite**
> List&lt;StatistiqueMembre&gt; getStatistiquesMembreCollectivite(idCollectivite, dateDebut, dateFin)

Statistiques détaillées par membre actif

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.StatistiquesApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.federation-agricole.mg/v1");

    StatistiquesApi apiInstance = new StatistiquesApi(defaultClient);
    Integer idCollectivite = 56; // Integer | 
    LocalDate dateDebut = LocalDate.now(); // LocalDate | 
    LocalDate dateFin = LocalDate.now(); // LocalDate | 
    try {
      List<StatistiqueMembre> result = apiInstance.getStatistiquesMembreCollectivite(idCollectivite, dateDebut, dateFin);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling StatistiquesApi#getStatistiquesMembreCollectivite");
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
| **dateDebut** | **LocalDate**|  | |
| **dateFin** | **LocalDate**|  | |

### Return type

[**List&lt;StatistiqueMembre&gt;**](StatistiqueMembre.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Liste des membres actifs avec taux assiduité, encaissements et impayés |  -  |
| **404** | Collectivité introuvable |  -  |

