# MembresApi

All URIs are relative to *https://api.federation-agricole.mg/v1*

| Method | HTTP request | Description |
|------------- | ------------- | -------------|
| [**adhererMembre**](MembresApi.md#adhererMembre) | **POST** /membres | Adhérer à une collectivité (nouveau membre) — conditions mises à jour B-2 |
| [**changerCollectivite**](MembresApi.md#changerCollectivite) | **PUT** /membres/{id_membre}/changer-collectivite | Changer de collectivité |
| [**demissionner**](MembresApi.md#demissionner) | **POST** /membres/{id_membre}/demission | Démissionner de la collectivité actuelle |


<a id="adhererMembre"></a>
# **adhererMembre**
> MembreResponse adhererMembre(adhererMembreRequest)

Adhérer à une collectivité (nouveau membre) — conditions mises à jour B-2

Crée un nouveau membre et l&#39;inscrit dans une collectivité. Nouvelles conditions (B-2) en vigueur depuis le 21/04/2026 : - Être parrainé par **au moins deux membres confirmés** de la fédération. - Le nombre de parrains issus de la collectivité cible doit être **≥** au nombre   de parrains provenant d&#39;autres collectivités.   - Ex. valide   : 2 parrains de la coll. cible + 0 externe ✔   - Ex. valide   : 1 parrain de la coll. cible + 1 d&#39;une autre ✔   - Ex. valide   : 3 parrains coll. cible + 2 externes ✔   - Ex. refusé   : 0 parrain coll. cible + 2 externes ✗ - Fournir ses informations personnelles + la **nature de la relation** avec chaque parrain   (famille, amis, collègues, etc.). - Régler les **frais d&#39;adhésion (50 000 MGA)** + l&#39;intégralité des   **cotisations annuelles obligatoires** imposées par la collectivité cible. 

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.MembresApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.federation-agricole.mg/v1");

    MembresApi apiInstance = new MembresApi(defaultClient);
    AdhererMembreRequest adhererMembreRequest = new AdhererMembreRequest(); // AdhererMembreRequest | 
    try {
      MembreResponse result = apiInstance.adhererMembre(adhererMembreRequest);
      System.out.println(result);
    } catch (ApiException e) {
      System.err.println("Exception when calling MembresApi#adhererMembre");
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
| **adhererMembreRequest** | [**AdhererMembreRequest**](AdhererMembreRequest.md)|  | |

### Return type

[**MembreResponse**](MembreResponse.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **201** | Membre créé et adhésion enregistrée |  -  |
| **400** | Conditions non remplies. Exemples de causes : - Moins de deux parrains fournis - Règle de majorité des parrains non respectée - Un parrain n&#39;est pas membre confirmé ou son ancienneté est ≤ 90 jours - Informations personnelles incomplètes  |  -  |
| **402** | Paiement des frais d&#39;adhésion ou des cotisations annuelles requis |  -  |
| **404** | Collectivité ou parrain introuvable |  -  |

<a id="changerCollectivite"></a>
# **changerCollectivite**
> changerCollectivite(idMembre, changerCollectiviteRequest)

Changer de collectivité

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.MembresApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.federation-agricole.mg/v1");

    MembresApi apiInstance = new MembresApi(defaultClient);
    Integer idMembre = 56; // Integer | 
    ChangerCollectiviteRequest changerCollectiviteRequest = new ChangerCollectiviteRequest(); // ChangerCollectiviteRequest | 
    try {
      apiInstance.changerCollectivite(idMembre, changerCollectiviteRequest);
    } catch (ApiException e) {
      System.err.println("Exception when calling MembresApi#changerCollectivite");
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
| **changerCollectiviteRequest** | [**ChangerCollectiviteRequest**](ChangerCollectiviteRequest.md)|  | |

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
| **200** | Collectivité mise à jour |  -  |
| **400** | Conditions de changement non remplies |  -  |
| **404** | Membre ou collectivité introuvable |  -  |

<a id="demissionner"></a>
# **demissionner**
> demissionner(idMembre)

Démissionner de la collectivité actuelle

### Example
```java
// Import classes:
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.Configuration;
import org.openapitools.client.models.*;
import org.openapitools.client.api.MembresApi;

public class Example {
  public static void main(String[] args) {
    ApiClient defaultClient = Configuration.getDefaultApiClient();
    defaultClient.setBasePath("https://api.federation-agricole.mg/v1");

    MembresApi apiInstance = new MembresApi(defaultClient);
    Integer idMembre = 56; // Integer | 
    try {
      apiInstance.demissionner(idMembre);
    } catch (ApiException e) {
      System.err.println("Exception when calling MembresApi#demissionner");
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

### Return type

null (empty response body)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: Not defined

### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
| **200** | Démission enregistrée (membre n&#39;a plus de collectivité active) |  -  |
| **404** | Membre introuvable |  -  |

