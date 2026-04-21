

# Mandat


## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**idMembre** | **Integer** |  |  |
|**poste** | [**PosteEnum**](#PosteEnum) |  |  |
|**entiteType** | [**EntiteTypeEnum**](#EntiteTypeEnum) |  |  |
|**entiteId** | **Integer** |  |  |
|**dateDebut** | **LocalDate** | Pour une collectivité : 1er janvier de l&#39;année civile. Pour la fédération : 1er janvier, mandat de 2 ans.  |  |
|**dateFin** | **LocalDate** | Pour une collectivité : 31 décembre de l&#39;année civile. Pour la fédération : 31 décembre de l&#39;année N+1.  |  |
|**idMandat** | **Integer** |  |  [optional] |
|**numeroMandat** | **Integer** | Numéro de mandat du membre pour ce poste (max 2). |  [optional] |



## Enum: PosteEnum

| Name | Value |
|---- | -----|
| PR_SIDENT | &quot;président&quot; |
| PR_SIDENT_ADJOINT | &quot;président adjoint&quot; |
| TR_SORIER | &quot;trésorier&quot; |
| SECR_TAIRE | &quot;secrétaire&quot; |



## Enum: EntiteTypeEnum

| Name | Value |
|---- | -----|
| COLLECTIVITE | &quot;collectivite&quot; |
| FEDERATION | &quot;federation&quot; |



