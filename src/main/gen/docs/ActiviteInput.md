

# ActiviteInput


## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**entiteType** | [**EntiteTypeEnum**](#EntiteTypeEnum) |  |  |
|**entiteId** | **Integer** |  |  |
|**titre** | **String** |  |  |
|**typeActivite** | [**TypeActiviteEnum**](#TypeActiviteEnum) |  |  |
|**dateHeure** | **OffsetDateTime** |  |  |
|**obligatoire** | **Boolean** |  |  |
|**publicCible** | **String** | Optionnel. Précise si l&#39;activité est réservée à un sous-groupe. |  [optional] |



## Enum: EntiteTypeEnum

| Name | Value |
|---- | -----|
| COLLECTIVITE | &quot;collectivite&quot; |
| FEDERATION | &quot;federation&quot; |



## Enum: TypeActiviteEnum

| Name | Value |
|---- | -----|
| AG_MENSUELLE | &quot;AG mensuelle&quot; |
| FORMATION_JUNIORS | &quot;formation juniors&quot; |
| EXCEPTIONNELLE | &quot;exceptionnelle&quot; |
| F_D_RALE | &quot;fédérale&quot; |



