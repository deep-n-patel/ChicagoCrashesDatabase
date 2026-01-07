
# Cleaning Crashes
import pandas as pd
df = pd.read_csv('crashes.csv')

print(type(df['CRASH_DATE']))

df['CRASH_DATE'] = pd.to_datetime(df['CRASH_DATE'], format='%m/%d/%Y %I:%M:%S %p', errors='coerce')
df['DATE'] = df['CRASH_DATE'].dt.strftime('%m/%d/%Y')
df['TIME'] = df['CRASH_DATE'].dt.strftime('%I:%M:%S %p')
df['YEAR'] = df['CRASH_DATE'].dt.year
print(df[['CRASH_DATE', 'DATE', 'TIME', 'YEAR']].head())
df = df[df['YEAR'].isin([2024, 2023, 2022])]

df = df.drop(columns=[
    'PHOTOS_TAKEN_I',
    'STATEMENTS_TAKEN_I',
    'DOORING_I',
    'WORK_ZONE_I',
    'WORK_ZONE_TYPE',
    'WORKERS_PRESENT_I',
    'LONGITUDE',
    'LATITUDE',
    'LOCATION',
    'CRASH_DATE'
])


df.to_csv('cleaned_data.csv', index=False)
