# cleaning Vehicles 
import pandas as pd
import numpy as np

df = pd.read_csv('Vehicles.csv')

df['CRASH_DATE'] = pd.to_datetime(df['CRASH_DATE'], format='%m/%d/%Y %I:%M:%S %p', errors='coerce')

df['DATE'] = df['CRASH_DATE'].dt.strftime('%m/%d/%Y')
df['TIME'] = df['CRASH_DATE'].dt.strftime('%I:%M:%S %p')
df['YEAR'] = df['CRASH_DATE'].dt.year
print(df[['CRASH_DATE', 'DATE', 'TIME', 'YEAR']].head())

df = df[df['YEAR'].isin([2024, 2023, 2022])]

df = df.drop(columns=[
        'CMRC_VEH_I', 'FIRE_I', 'AREA_00_I', 'AREA_01_I', 'AREA_02_I', 'AREA_03_I', 'AREA_04_I', 'AREA_05_I', 'AREA_06_I',
    'AREA_07_I', 'AREA_08_I', 'AREA_09_I', 'AREA_10_I', 'AREA_11_I', 'AREA_12_I', 'AREA_99_I', "CMV_ID", "USDOT_NO", "CCMC_NO", "ILCC_NO", "COMMERCIAL_SRC", "GVWR", "CARRIER_NAME",
    "CARRIER_STATE", "CARRIER_CITY", "HAZMAT_PLACARDS_I", "HAZMAT_NAME", "UN_NO",
    "HAZMAT_PRESENT_I", "HAZMAT_REPORT_I", "HAZMAT_REPORT_NO", "MCS_REPORT_I",
    "MCS_REPORT_NO", "HAZMAT_VIO_CAUSE_CRASH_I", "MCS_VIO_CAUSE_CRASH_I",
    "IDOT_PERMIT_NO", "WIDE_LOAD_I", "TRAILER1_WIDTH", "TRAILER2_WIDTH", "TRAILER1_LENGTH",
    "TRAILER2_LENGTH", "TOTAL_VEHICLE_LENGTH", "AXLE_CNT", "VEHICLE_CONFIG", "CARGO_BODY_TYPE",
    "LOAD_TYPE", "HAZMAT_OUT_OF_SERVICE_I", "MCS_OUT_OF_SERVICE_I", "HAZMAT_CLASS"])
df.to_csv('cleaned_data_Vehicles.csv', index=False)