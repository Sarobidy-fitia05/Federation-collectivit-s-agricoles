

# CotisationInput


## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**idMembre** | **Integer** |  |  |
|**idCollectivite** | **Integer** |  |  |
|**montant** | **Double** |  |  |
|**dateEncaissement** | **LocalDate** |  |  |
|**modePaiement** | [**ModePaiementEnum**](#ModePaiementEnum) |  |  |
|**typeCotisation** | [**TypeCotisationEnum**](#TypeCotisationEnum) |  |  |



## Enum: ModePaiementEnum

| Name | Value |
|---- | -----|
| ESP_CE | &quot;espèce&quot; |
| VIREMENT_BANCAIRE | &quot;virement bancaire&quot; |
| MOBILE_MONEY | &quot;mobile money&quot; |



## Enum: TypeCotisationEnum

| Name | Value |
|---- | -----|
| MENSUELLE | &quot;mensuelle&quot; |
| ANNUELLE | &quot;annuelle&quot; |
| PONCTUELLE | &quot;ponctuelle&quot; |



