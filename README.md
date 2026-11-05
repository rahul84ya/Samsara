# Dhara (ధర) - Andhra Pradesh Land Records & MeeBhoomi Gateway

A high-performance native Android application built with **Kotlin** and **Jetpack Compose (Material 3)** featuring an earthy warm orange/saffron theme. **Dhara** provides direct access to Andhra Pradesh land records (Adangal / Pahani, ROR 1-B, and Cadastral FMB survey maps) following the standardized 4-step administrative hierarchy:
**District ➔ Tahseel / Mandal ➔ Revenue Village ➔ Survey Number**.

---

## Key Features

1. **Structured Dropdown Flow**:
   - **Step 1: District (జిల్లా)**: Covers all 28 administrative units of Andhra Pradesh with official LGD codes (State LGD: 28).
   - **Step 2: Tahseel / Mandal (తహసీల్ / మండలం)**: Dynamically cascades based on selected district with LGD code badges.
   - **Step 3: Revenue Village (రెవెన్యూ గ్రామం)**: Dynamically cascades based on selected mandal with registered survey counts and LGD codes.
   - **Step 4: Survey Number & Sub-division**: Quick select chips from the village survey catalogue or custom input.

2. **Official AP Land Documents**:
   - **Adangal / Pahani (గ్రామ అడంగల్)**: Comprehensive village land register with Pattadar name, occupant details, crop cultivation, soil classification, water source, and extent.
   - **ROR 1-B (హక్కుల పత్రం)**: Record of Rights certifying ownership title, passbook khata numbers, mutation history, and land tenure.
   - **FMB Cadastral Map (ఫీల్డ్ మెజర్‌మెంట్ బుక్)**: Vector cadastral map viewer rendering survey boundary stones (A, B, C, D), tie-lines, side lengths in links/meters, cardinal boundaries, and adjacent survey parcel labels.

3. **Multi-Mode Search**:
   - By Survey Number / Sub-division
   - By Khata Number
   - By Pattadar Name (with interactive village-level landowner directory modal)
   - By LP Number (ULPIN - 14-digit Bhu-Aadhaar)

4. **LGD & MeeBhoomi Integration & Compliance**:
   - Local Government Directory (LGD) derived data under GODL-India license (28 districts, 688 mandals, 17,954 revenue villages).
   - Direct integration endpoints for AP MeeBhoomi (`meebhoomi.ap.gov.in`).
   - Official independent legal disclaimer and compliance notice.

5. **Offline Storage & Sharing**:
   - Save records locally on-device for instant offline reference.
   - Share formatted land record reports directly via WhatsApp, SMS, or email.
