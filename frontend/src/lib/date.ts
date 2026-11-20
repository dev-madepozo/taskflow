export function formatDate(
    date: string | null | undefined,
): string {
    if (!date) return '—'

    const parsedDate = new Date(`${date.slice(0, 10)}T00:00:00`)

    if (Number.isNaN(parsedDate.getTime())) return '—'

    return new Intl.DateTimeFormat('en-US', {
        month: 'short',
        day: '2-digit',
        year: 'numeric',
    }).format(parsedDate)
}