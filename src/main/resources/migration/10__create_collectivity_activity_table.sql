create table if not exists "collectivity_activity"
(
    id              varchar primary key,
    collectivity_id varchar references "collectivity" (id) not null,
    label           varchar,
    activity_type   varchar not null check (activity_type in ('MEETING', 'TRAINING', 'OTHER')),
    executive_date  date,
    recurrence_week_ordinal integer,
    recurrence_day_of_week varchar check (recurrence_day_of_week in ('MO', 'TU', 'WE', 'TH', 'FR', 'SA', 'SU'))
    );