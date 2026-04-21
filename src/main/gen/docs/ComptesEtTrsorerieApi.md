# ComptesEtTrsorerieApi

All URIs are relative to *https://api.federation-agricole.mg/v1*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**creerCaisse**](ComptesEtTrsorerieApi.md#creerCaisse) | **POST** /comptes/caisse | Créer un compte de type caisse (une seule par entité) |
| [**creerCompteBancaire**](ComptesEtTrsorerieApi.md#creerCompteBancaire) | **POST** /comptes/bancaire | Ajouter un compte bancaire |
| [**creerCompteMobileMoney**](ComptesEtTrsorerieApi.md#creerCompteMobileMoney) | **POST** /comptes/mobile-money | Ajouter un compte mobile money |
| [**getSoldeCompte**](ComptesEtTrsorerieApi.md#getSoldeCompte) | **GET** /comptes/{id_compte}/solde | Consulter le solde d&#39;un compte à une date donnée |


<a id="creerCaisse"></a>
# **creerCaisse**
> creerCaisse(creerCaisseRequest)

Créer un compte de type caisse (une seule par entité)

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.ComptesEtTrsorerieApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.federation-agricole.mg/v1");

    ComptesEtTrsorerieApi apiInstance = new ComptesEtTrsorerieApi(defaultClient);
    CreerCaisseRequest creerCaisseRequest = new CreerCaisseRequest(); // CreerCaisseRequest | 
    try {
      apiInstance.creerCaisse(creerCaisseRequest);
    } catch (ApiException e) {
      System.err.println("Exception when calling ComptesEtTrsorerieApi#creerCaisse");
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
| **creerCaisseRequest** | [**CreerCaisseRequest**](CreerCaisseRequest.md)|  | |

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
| **201** | Caisse créée |  -  |
| **404** | Entité introuvable |  -  |
| **409** | Une caisse existe déjà pour cette entité |  -  |

<a id="creerCompteBancaire"></a>
# **creerCompteBancaire**
> creerCompteBancaire(compteBancaireInput)

Ajouter un compte bancaire

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.ComptesEtTrsorerieApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.federation-agricole.mg/v1");

    ComptesEtTrsorerieApi apiInstance = new ComptesEtTrsorerieApi(defaultClient);
    CompteBancaireInput compteBancaireInput = new CompteBancaireInput(); // CompteBancaireInput | 
    try {
      apiInstance.creerCompteBancaire(compteBancaireInput);
    } catch (ApiException e) {
      System.err.println("Exception when calling ComptesEtTrsorerieApi#creerCompteBancaire");
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
| **compteBancaireInput** | [**CompteBancaireInput**](CompteBancaireInput.md)|  | |

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
| **201** | Compte bancaire créé |  -  |
| **404** | Entité introuvable |  -  |

<a id="creerCompteMobileMoney"></a>
# **creerCompteMobileMoney**
> creerCompteMobileMoney(compteMobileMoneyInput)

Ajouter un compte mobile money

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.ComptesEtTrsorerieApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.federation-agricole.mg/v1");

    ComptesEtTrsorerieApi apiInstance = new ComptesEtTrsorerieApi(defaultClient);
    CompteMobileMoneyInput compteMobileMoneyInput = new CompteMobileMoneyInput(); // CompteMobileMoneyInput | 
    try {
      apiInstance.creerCompteMobileMoney(compteMobileMoneyInput);
    } catch (ApiException e) {
      System.err.println("Exception when calling ComptesEtTrsorerieApi#creerCompteMobileMoney");
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
| **compteMobileMoneyInput** | [**CompteMobileMoneyInput**](CompteMobileMoneyInput.md)|  | |

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
| **201** | Compte mobile money créé |  -  |
| **404** | Entité introuvable |  -  |

<a id="getSoldeCompte"></a>
# **getSoldeCompte**
> GetSoldeCompte200Response getSoldeCompte(idCompte, date)

Consulter le solde d&#39;un compte à une date donnée

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.ComptesEtTrsorerieApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.federation-agricole.mg/v1");

    ComptesEtTrsorerieApi apiInstance = new ComptesEtTrsorerieApi(defaultClient);
    Integer idCompte = 56; // Integer | 
    LocalDate date = LocalDate.parse("Tue Apr 21 03:00:00 EAT 2026"); // LocalDate | 
    try {
      GetSoldeCompte200Response result = apiInstance.getSoldeCompte(idCompte, date);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling ComptesEtTrsorerieApi#getSoldeCompte");
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
| **idCompte** | **Integer**|  | |
| **date** | **LocalDate**|  | [optional] |

### Return type

[**GetSoldeCompte200Response**](GetSoldeCompte200Response.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Solde et date du solde |  -  |
| **404** | Compte introuvable |  -  |

