

# MembreResponse


## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**nom** | **String** |  |  |
|**prenom** | **String** |  |  |
|**dateNaissance** | **LocalDate** |  |  |
|**genre** | [**GenreEnum**](#GenreEnum) |  |  |
|**adresse** | **String** |  |  |
|**metier** | **String** |  |  |
|**telephone** | **String** |  |  |
|**email** | **String** |  |  |
|**dateAdhesion** | **LocalDate** |  |  |
|**idMembre** | **Integer** |  |  [optional] |
|**idCollectivite** | **Integer** |  |  [optional] |
|**poste** | [**PosteEnum**](#PosteEnum) |  |  [optional] |



## Enum: GenreEnum

| Name | Value |
|---- | -----|
| F_MININ | &quot;féminin&quot; |
| MASCULIN | &quot;masculin&quot; |



## Enum: PosteEnum

| Name | Value |
|---- | -----|
| PR_SIDENT | &quot;président&quot; |
| PR_SIDENT_ADJOINT | &quot;président adjoint&quot; |
| TR_SORIER | &quot;trésorier&quot; |
| SECR_TAIRE | &quot;secrétaire&quot; |
| MEMBRE_CONFIRM_ | &quot;membre confirmé&quot; |
| MEMBRE_JUNIOR | &quot;membre junior&quot; |



