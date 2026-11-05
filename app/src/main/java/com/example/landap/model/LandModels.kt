package com.example.landap.model

enum class DocumentType(val title: String, val code: String, val description: String) {
    ADANGAL("Adangal / Pahani", "ADANGAL", "Village Land Record with crop, tenancy & occupant details"),
    ROR_1B("ROR 1-B", "ROR1B", "Record of Rights certifying legal title, patta & mutations"),
    FMB("FMB Cadastral Map", "FMB", "Field Measurement Book with land parcel boundaries & dimensions")
}

enum class SearchMode(val label: String, val placeholder: String, val hint: String) {
    SURVEY_NO("Survey No", "e.g. 29/3, 142/2A, 102/1", "Select or enter survey number & sub-division"),
    KHATA_NO("Khata No", "e.g. 22, 145, 518, 1024", "Enter landowner passbook khata account number"),
    PATTADAR_NAME("Pattadar Name", "e.g. Padmavathi, Koteswara Rao", "Search landowner name in village directory"),
    LP_NO("LP No (ULPIN)", "e.g. LP-00452, 2809100142", "Enter 14-digit Unique Land Parcel Identification Number")
}

data class District(
    val id: String,
    val lgdCode: Int,
    val name: String,
    val teluguName: String,
    val headquarters: String,
    val mandalCount: Int
)

data class Mandal(
    val id: String,
    val lgdCode: Int,
    val districtId: String,
    val name: String,
    val teluguName: String,
    val division: String = "Revenue Division"
)

data class Village(
    val code: String,
    val lgdCode: Int,
    val mandalId: String,
    val name: String,
    val teluguName: String,
    val totalSurveys: Int,
    val sampleSurveys: List<String> = listOf("29/1", "29/2", "29/3", "31/1", "45/A", "45/B", "73/1", "88/1A", "88/1B", "102/1", "142/2A", "160/2", "188/3")
)

data class PattadarEntry(
    val id: String,
    val villageCode: String,
    val pattadarName: String,
    val fatherOrHusbandName: String,
    val khataNo: String,
    val surveyNos: List<String>,
    val totalExtentAcres: Double
)

data class BoundaryPoint(
    val x: Float,
    val y: Float,
    val label: String = "",
    val sideLengthMeters: Double = 0.0
)

data class LandRecord(
    val id: String,
    val documentType: DocumentType,
    val district: String,
    val districtLgd: Int,
    val mandal: String,
    val mandalLgd: Int,
    val village: String,
    val villageLgd: Int,
    val surveyNo: String,
    val khataNo: String,
    val lpNumber: String,
    val pattadarName: String,
    val pattadarFatherName: String,
    val occupantName: String,
    val occupantFatherName: String,
    val totalExtent: String,
    val natureOfLand: String,
    val landClassification: String,
    val waterSource: String,
    val soilType: String,
    val marketValuePerAcre: String,
    val lastMutationDate: String,
    val remarks: String,
    val boundaryPoints: List<BoundaryPoint>,
    val adjacentSurveys: Map<String, String>, // North, South, East, West
    val verifiedOnPortal: Boolean = true,
    val isSaved: Boolean = false,
    val savedAtTimestamp: Long = 0L,
    val meesevaTxnId: String = "AP-MB-2026-981245",
    val section22AStatus: String = "CLEAR (Not in Section 22-A Prohibited List)",
    val cultivationCrop: String = "Paddy / Rice (వరి - మొదటి పంట సాగులో ఉంది)",
    val landRevenueCess: String = "₹ 142.50 / సంవత్సరం",
    val titleAcquisitionMode: String = "Ancestral Succession (వారసత్వ హక్కు)",
    val subRegistrarOffice: String = "Sub-Registrar Office, AP Stamps & Registration",
    val digitalSignatureInfo: String = "Digitally Signed by Tahsildar & CCLA MeeBhoomi Gateway"
)

data class PortalStatus(
    val isLive: Boolean = true,
    val portalUrl: String = "https://meebhoomi.ap.gov.in",
    val lgdPortalUrl: String = "https://panchayat.gov.in/en/lgd/",
    val latencyMs: Long = 165L,
    val lastCheckedText: String = "Active (Connected)"
)
