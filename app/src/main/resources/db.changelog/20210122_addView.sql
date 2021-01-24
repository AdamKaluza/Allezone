CREATE OR REPLACE VIEW auction_view as
SELECT
    au.id as auction_id,
    au.title as auctionTitle,
    au.price as price,
    ap.name as miniature
FROM
    auction au, auction_photos ap
WHERE ap.position = 1 AND ap.auction_id=au.id;

