

# CompteBancaireInput


## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**entiteType** | [**EntiteTypeEnum**](#EntiteTypeEnum) |  |  |
|**entiteId** | **Integer** |  |  |
|**titulaire** | **String** |  |  |
|**nomBanque** | [**NomBanqueEnum**](#NomBanqueEnum) |  |  |
|**numeroCompte** | **String** | 23 chiffres (5+5+11+2) — espaces acceptés |  |
|**solde** | **Double** |  |  |
|**dateSolde** | **LocalDate** |  |  |



## Enum: EntiteTypeEnum

| Name | Value |
|---- | -----|
| COLLECTIVITE | &quot;collectivite&quot; |
| FEDERATION | &quot;federation&quot; |



## Enum: NomBanqueEnum

| Name | Value |
|---- | -----|
| BRED | &quot;BRED&quot; |
| MCB | &quot;MCB&quot; |
| BMOI | &quot;BMOI&quot; |
| BOA | &quot;BOA&quot; |
| BGFI | &quot;BGFI&quot; |
| AFG | &quot;AFG&quot; |
| ACC_S_BANQUE | &quot;ACCÈS BANQUE&quot; |
| BAOBAB | &quot;BAOBAB&quot; |
| SIPEM | &quot;SIPEM&quot; |



