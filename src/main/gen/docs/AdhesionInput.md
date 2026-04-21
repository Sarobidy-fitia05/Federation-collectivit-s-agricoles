

# AdhesionInput


## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**idCollectiviteCible** | **Integer** | Collectivité dans laquelle le candidat veut adhérer. |  |
|**modePaiementFrais** | [**ModePaiementFraisEnum**](#ModePaiementFraisEnum) | Mode de paiement des frais d&#39;adhésion (50 000 MGA). |  |
|**modePaiementCotisationsAnnuelles** | [**ModePaiementCotisationsAnnuellesEnum**](#ModePaiementCotisationsAnnuellesEnum) | Mode de paiement des cotisations annuelles obligatoires imposées par la collectivité cible.  |  |
|**montantCotisationsAnnuelles** | **Double** | Montant total des cotisations annuelles à régler (déterminé par la collectivité). Champ en lecture seule, renvoyé par le serveur pour confirmation.  |  [optional] [readonly] |



## Enum: ModePaiementFraisEnum

| Name | Value |
|---- | -----|
| MOBILE_MONEY | &quot;mobile money&quot; |
| VIREMENT_BANCAIRE | &quot;virement bancaire&quot; |



## Enum: ModePaiementCotisationsAnnuellesEnum

| Name | Value |
|---- | -----|
| MOBILE_MONEY | &quot;mobile money&quot; |
| VIREMENT_BANCAIRE | &quot;virement bancaire&quot; |
| ESP_CE | &quot;espèce&quot; |



